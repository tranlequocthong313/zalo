package vn.edu.ou.zalo.data.sources.remote;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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
        checkAddedFriend(friend, new IRepositoryCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean isAdded) {
                if (isAdded) {
                    return;
                }

                Friendship friendship = new Friendship();
                friendship.setReceiverId(friend.getId());
                friendship.setSenderId(signedInUser.getId());

                db
                        .collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                        .add(friendship)
                        .addOnSuccessListener(d -> {
                            friendship.setId(d.getId());
                            cb.onSuccess(friendship);
                        })
                        .addOnFailureListener(cb::onFailure);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }

    @Override
    public void checkAddedFriend(User user, IRepositoryCallback<Boolean> cb) {
        db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                .whereEqualTo("status", Friendship.Status.ACCEPTED)
                .whereEqualTo("senderId", signedInUser.getId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        cb.onSuccess(true);
                        return;
                    }

                    db.collection(Constants.FRIENDSHIP_COLLECTION_NAME)
                            .whereEqualTo("status", Friendship.Status.ACCEPTED)
                            .whereEqualTo("receiverId", signedInUser.getId())
                            .get()
                            .addOnSuccessListener(doc -> {
                                cb.onSuccess(!doc.isEmpty());
                            }).addOnFailureListener(cb::onFailure);
                })
                .addOnFailureListener(cb::onFailure);
    }

    @Override
    public void getAddedFriends(IRepositoryCallback<List<User>> cb) {
        List<User> friends = new ArrayList<>();

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
}
