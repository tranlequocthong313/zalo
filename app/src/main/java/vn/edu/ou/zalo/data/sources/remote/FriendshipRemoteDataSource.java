package vn.edu.ou.zalo.data.sources.remote;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IFriendshipDataSource;
import vn.edu.ou.zalo.utils.Constants;

public class FriendshipRemoteDataSource implements IFriendshipDataSource {
    private final FirebaseFirestore db;
    private User signedInUser;

    @Inject
    public FriendshipRemoteDataSource(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public void addFriend(User friend, IRepositoryCallback<Friendship> cb) {
        checkFriendStatus(friend, new IRepositoryCallback<Friendship.Status>() {
            @Override
            public void onSuccess(Friendship.Status status) {
                if (status != null) {
                    return;
                }

                String id = friend.getId() + signedInUser.getId();
                Friendship friendship = new Friendship();
                friendship.setId(id);
                friendship.setReceiverId(friend.getId());
                friendship.setSenderId(signedInUser.getId());

                db
                        .collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                        .document(id)
                        .set(friendship)
                        .addOnSuccessListener(doc -> cb.onSuccess(friendship))
                        .addOnFailureListener(cb::onFailure);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }

    @Override
    public void checkFriendStatus(User user, IRepositoryCallback<Friendship.Status> cb) {
        String id1 = user.getId() + signedInUser.getId();
        String id2 = signedInUser.getId() + user.getId();

        db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                .document(id1)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Friendship friendship = documentSnapshot.toObject(Friendship.class);
                        assert friendship != null;
                        cb.onSuccess(friendship.getStatus());
                        return;
                    }
                    db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                            .document(id2)
                            .get()
                            .addOnSuccessListener(doc -> {
                                if (doc.exists()) {
                                    Friendship friendship = doc.toObject(Friendship.class);
                                    assert friendship != null;
                                    cb.onSuccess(friendship.getStatus());
                                    return;
                                }
                                cb.onSuccess(null);
                            }).addOnFailureListener(cb::onFailure);
                })
                .addOnFailureListener(cb::onFailure);
    }

    @Override
    public void getAddedFriends(IRepositoryCallback<List<User>> cb) {
        List<User> friends = new ArrayList<>();
        if (signedInUser == null) {
            cb.onFailure(new Exception("User is not signed in"));
            return;
        }

        db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                .whereEqualTo("status", Friendship.Status.ACCEPTED)
                .whereEqualTo("senderId", signedInUser.getId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            User user = new User();
                            user.setId(document.getString("receiverId"));
                            friends.add(user);
                        }
                    }

                    db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                            .whereEqualTo("status", Friendship.Status.ACCEPTED)
                            .whereEqualTo("receiverId", signedInUser.getId())
                            .get()
                            .addOnSuccessListener(userSnapshot -> {
                                for (DocumentSnapshot document : userSnapshot.getDocuments()) {
                                    User user = new User();
                                    user.setId(document.getString("senderId"));
                                    if (!isContainedUser(friends, user)) {
                                        friends.add(user);
                                    }
                                }

                                List<String> friendIds = new ArrayList<>();
                                for (User friend : friends) {
                                    friendIds.add(friend.getId());
                                }

                                if (!friendIds.isEmpty()) {
                                    db.collection(Constants.USER_COLLECTION_NAME)
                                            .whereIn(FieldPath.documentId(), friendIds)
                                            .get()
                                            .addOnSuccessListener(docSnapshot -> {
                                                for (DocumentSnapshot doc : docSnapshot.getDocuments()) {
                                                    String userId = doc.getId();
                                                    for (User friend : friends) {
                                                        if (friend.getId().equals(userId)) {
                                                            friend.setAvatarUrl(doc.getString("avatarUrl"));
                                                            friend.setFullName(doc.getString("fullName"));
                                                            friend.setIsOnline(Boolean.TRUE.equals(doc.getBoolean("online")));
                                                        }
                                                    }
                                                }
                                                cb.onSuccess(friends);
                                            })
                                            .addOnFailureListener(cb::onFailure);
                                } else {
                                    cb.onSuccess(friends);
                                }
                            });
                })
                .addOnFailureListener(cb::onFailure);
    }

    private boolean isContainedUser(List<User> users, User user) {
        for (User u : users) {
            if (Objects.equals(u.getId(), user.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setSignedInUser(User user) {
        this.signedInUser = user;
    }

    @Override
    public void getRecommendedFriends(IRepositoryCallback<List<User>> cb) {
        Set<String> friendIds = new HashSet<>();

        db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                .whereEqualTo("status", Friendship.Status.ACCEPTED)
                .whereEqualTo("senderId", signedInUser.getId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            friendIds.add(document.getString("receiverId"));
                        }
                    }

                    db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                            .whereEqualTo("status", Friendship.Status.ACCEPTED)
                            .whereEqualTo("receiverId", signedInUser.getId())
                            .get()
                            .addOnCompleteListener(receiverTask -> {
                                if (receiverTask.isSuccessful()) {
                                    for (DocumentSnapshot document : receiverTask.getResult()) {
                                        friendIds.add(document.getString("senderId"));
                                    }
                                }

                                db.collection(Constants.USER_COLLECTION_NAME)
                                        .whereNotEqualTo("id", signedInUser.getId())
                                        .get()
                                        .addOnSuccessListener(userSnapshot -> {
                                            List<User> recommendedUsers = new ArrayList<>();
                                            for (DocumentSnapshot document : userSnapshot.getDocuments()) {
                                                User user = document.toObject(User.class);
                                                assert user != null;
                                                user.setId(document.getId());
                                                if (!friendIds.contains(user.getId())) {
                                                    recommendedUsers.add(user);
                                                }
                                            }
                                            cb.onSuccess(recommendedUsers);
                                        })
                                        .addOnFailureListener(cb::onFailure);
                            });
                })
                .addOnFailureListener(cb::onFailure);
    }

    @Override
    public void getFriendRequests(boolean isReceived, IRepositoryCallback<List<Friendship>> cb) {

        db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                .whereEqualTo("status", Friendship.Status.PENDING.name())
                .whereEqualTo(isReceived ? "receiverId" : "senderId", signedInUser.getId())
                .get()
                .addOnSuccessListener(docs -> {
                    List<Task<DocumentSnapshot>> userTasks = new ArrayList<>();
                    List<Friendship> friendships = new ArrayList<>();
                    for (DocumentSnapshot doc : docs) {
                        Friendship friendship = doc.toObject(Friendship.class);
                        assert friendship != null;
                        friendship.setId(doc.getId());
                        Task<DocumentSnapshot> task = db.collection(Constants.USER_COLLECTION_NAME)
                                .document(Objects.requireNonNull(doc.getString(isReceived ? "senderId" : "receiverId")))
                                .get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    User user = documentSnapshot.toObject(User.class);
                                    assert user != null;
                                    user.setId(documentSnapshot.getId());
                                    if (isReceived) {
                                        friendship.setSender(user);
                                    } else {
                                        friendship.setReceiver(user);
                                    }
                                    friendships.add(friendship);
                                })
                                .addOnFailureListener(cb::onFailure);
                        userTasks.add(task);
                    }

                    Tasks.whenAllComplete(userTasks)
                            .addOnSuccessListener(tasks -> cb.onSuccess(friendships))
                            .addOnFailureListener(cb::onFailure);
                })
                .addOnFailureListener(cb::onFailure);
    }

    @Override
    public void updateFriendshipStatus(Friendship friendship, IRepositoryCallback<Void> cb) {
        db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                .document(friendship.getId())
                .set(friendship, SetOptions.mergeFields(Collections.singletonList("status")))
                .addOnSuccessListener(cb::onSuccess)
                .addOnFailureListener(cb::onFailure);
    }

    @Override
    public void deleteFriendship(String id, IRepositoryCallback<Void> cb) {
        db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                .document(id)
                .delete()
                .addOnSuccessListener(cb::onSuccess)
                .addOnFailureListener(cb::onFailure);
    }
}
