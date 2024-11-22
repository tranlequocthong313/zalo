package vn.edu.ou.zalo.data.sources.remote;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.utils.Constants;

public class ChatRoomRemoteDataSource implements IChatRoomDataSource {
    private User signedInUser;
    private final FirebaseFirestore db;

    @Inject
    public ChatRoomRemoteDataSource(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public void getChatRooms(Map<String, String> query, IRepositoryCallback<List<ChatRoom>> callback) {
        Query chatRoomCollection = db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .whereNotEqualTo("lastMessage", null);

        if (query != null && query.containsKey("priority") && query.get("priority") != null) {
            ChatRoom.Priority priority = ChatRoom.Priority.valueOf(query.get("priority"));
            chatRoomCollection = chatRoomCollection.whereEqualTo("priority", priority.name());
        }
        if (query != null && query.containsKey("type") && query.get("type") != null) {
            ChatRoom.Type type = ChatRoom.Type.valueOf(query.get("type"));
            chatRoomCollection = chatRoomCollection.whereEqualTo("type", type.name());
        }

        chatRoomCollection
                .get()
                .addOnSuccessListener(docs -> {
                    List<ChatRoom> chatRooms = new ArrayList<>();
                    List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                    if (docs.isEmpty()) {
                        callback.onSuccess(chatRooms);
                        return;
                    }
                    for (DocumentSnapshot document : docs) {
                        ChatRoom chatRoom = document.toObject(ChatRoom.class);
                        assert chatRoom != null;
                        chatRoom.setId(document.getId());
                        db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                                .document(document.getId())
                                .collection("members")
                                .get()
                                .addOnSuccessListener(memberDocs -> {
                                    Set<ChatRoom.Member> members = new HashSet<>();
                                    boolean found = false;
                                    for (DocumentSnapshot doc : memberDocs) {
                                        ChatRoom.Member member = doc.toObject(ChatRoom.Member.class);
                                        members.add(member);
                                        if (doc.getId().equals(signedInUser.getId())) {
                                            found = true;
                                        }
                                    }
                                    if (found) {
                                        chatRoom.setMembers(members);
                                        chatRooms.add(chatRoom);
                                        for (ChatRoom.Member m : members) {
                                            Task<DocumentSnapshot> task = db.collection(Constants.USER_COLLECTION_NAME)
                                                    .document(m.getId())
                                                    .get()
                                                    .addOnSuccessListener(doc -> {
                                                        User u = doc.toObject(User.class);
                                                        assert u != null;
                                                        u.setId(doc.getId());
                                                        m.setUser(u);
                                                    })
                                                    .addOnFailureListener(callback::onFailure);
                                            tasks.add(task);
                                        }
                                    }

                                    Tasks.whenAllComplete(tasks)
                                            .addOnSuccessListener(t -> callback.onSuccess(chatRooms))
                                            .addOnFailureListener(callback::onFailure);
                                })
                                .addOnFailureListener(callback::onFailure);
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getChatRoom(String id, IRepositoryCallback<ChatRoom> callback) {
        db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .document(id)
                .get()
                .addOnSuccessListener(doc -> {
                    ChatRoom chatRoom = doc.toObject(ChatRoom.class);
                    assert chatRoom != null;
                    chatRoom.setId(doc.getId());

                    db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                            .document(chatRoom.getId())
                            .collection("members")
                            .get()
                            .addOnSuccessListener(memberDocs -> {
                                Set<ChatRoom.Member> members = new HashSet<>();
                                List<Task<DocumentSnapshot>> userTasks = new ArrayList<>();

                                for (DocumentSnapshot memDoc : memberDocs.getDocuments()) {
                                    Task<DocumentSnapshot> userTask = db.collection(Constants.USER_COLLECTION_NAME)
                                            .document(memDoc.getId())
                                            .get()
                                            .addOnSuccessListener(documentSnapshot -> {
                                                ChatRoom.Member member = memDoc.toObject(ChatRoom.Member.class);
                                                if (member != null) {
                                                    member.setId(memDoc.getId());
                                                    User u = documentSnapshot.toObject(User.class);
                                                    assert u != null;
                                                    u.setId(documentSnapshot.getId());
                                                    member.setUser(u);
                                                    members.add(member);
                                                }
                                            });
                                    userTasks.add(userTask);
                                }

                                Tasks.whenAllComplete(userTasks)
                                        .addOnSuccessListener(tasks -> {
                                            chatRoom.setMembers(members);
                                            callback.onSuccess(chatRoom);
                                        })
                                        .addOnFailureListener(callback::onFailure);
                            })
                            .addOnFailureListener(callback::onFailure);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getChatRoom(User user, IRepositoryCallback<ChatRoom> callback) {
        List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        AtomicReference<ChatRoom> chatRoom = new AtomicReference<>(null);
        AtomicReference<QuerySnapshot> memberQuerySnapShot = new AtomicReference<>(null);

        db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .whereEqualTo("type", ChatRoom.Type.SINGLE.name())
                .get()
                .addOnSuccessListener(chatRoomDocs -> {
                    for (DocumentSnapshot chatRoomDoc : chatRoomDocs.getDocuments()) {
                        String chatRoomId = chatRoomDoc.getId();

                        Task<QuerySnapshot> task = db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                                .document(chatRoomId)
                                .collection("members")
                                .get()
                                .addOnSuccessListener(memberDocs -> {
                                    List<String> memberIds = new ArrayList<>();
                                    for (DocumentSnapshot memberDoc : memberDocs.getDocuments()) {
                                        memberIds.add(memberDoc.getId());
                                    }

                                    if (memberIds.size() == 2 && memberIds.contains(user.getId()) && memberIds.contains(signedInUser.getId())) {
                                        ChatRoom c = chatRoomDoc.toObject(ChatRoom.class);
                                        assert c != null;
                                        c.setId(chatRoomDoc.getId());
                                        chatRoom.set(c);
                                        memberQuerySnapShot.set(memberDocs);
                                    }
                                })
                                .addOnFailureListener(callback::onFailure);

                        tasks.add(task);
                    }

                    Tasks.whenAllComplete(tasks)
                            .addOnSuccessListener(t -> {
                                Set<ChatRoom.Member> members = new HashSet<>();
                                List<Task<DocumentSnapshot>> userTasks = new ArrayList<>();

                                if (memberQuerySnapShot.get() != null) {
                                    for (DocumentSnapshot doc : memberQuerySnapShot.get().getDocuments()) {
                                        Task<DocumentSnapshot> userTask = db.collection(Constants.USER_COLLECTION_NAME)
                                                .document(doc.getId())
                                                .get()
                                                .addOnSuccessListener(documentSnapshot -> {
                                                    ChatRoom.Member member = doc.toObject(ChatRoom.Member.class);
                                                    if (member != null) {
                                                        member.setId(doc.getId());
                                                        member.setUser(documentSnapshot.toObject(User.class));
                                                        members.add(member);
                                                    }
                                                });
                                        userTasks.add(userTask);
                                    }

                                }

                                Tasks.whenAllComplete(userTasks)
                                        .addOnSuccessListener(t2 -> {
                                            if (chatRoom.get() != null) {
                                                chatRoom.get().setMembers(members);
                                            }
                                            callback.onSuccess(chatRoom.get());
                                        })
                                        .addOnFailureListener(callback::onFailure);

                            })
                            .addOnFailureListener(callback::onFailure);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void setSignedInUser(User signedInUser) {
        this.signedInUser = signedInUser;
    }


    @Override
    public void checkEmptyChatRoom(IRepositoryCallback<Map<ChatRoom.Priority, Boolean>> callback) {
        getChatRooms(null, new IRepositoryCallback<List<ChatRoom>>() {
            @Override
            public void onSuccess(List<ChatRoom> data) {
                Map<ChatRoom.Priority, Boolean> map = new HashMap<>();
                map.put(ChatRoom.Priority.FOCUSED, true);
                map.put(ChatRoom.Priority.OTHER, true);

                if (data == null || data.isEmpty()) {
                    callback.onSuccess(map);
                    return;
                }

                for (ChatRoom chatRoom : data) {
                    if (chatRoom.getPriority() == ChatRoom.Priority.FOCUSED) {
                        map.put(ChatRoom.Priority.FOCUSED, false);
                    }
                    if (chatRoom.getPriority() == ChatRoom.Priority.OTHER) {
                        map.put(ChatRoom.Priority.OTHER, false);
                    }
                }
                callback.onSuccess(map);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void createChatRoom(ChatRoom chatRoom, IRepositoryCallback<ChatRoom> callback) {
        db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .add(chatRoom)
                .addOnSuccessListener(doc -> {
                    chatRoom.setId(doc.getId());
                    List<Task<Void>> tasks = new ArrayList<>();
                    for (ChatRoom.Member member : chatRoom.getMembers()) {
                        Task<Void> task = doc.collection("members")
                                .document(member.getId())
                                .set(member)
                                .addOnFailureListener(callback::onFailure);
                        tasks.add(task);
                    }
                    Tasks.whenAllComplete(tasks)
                            .addOnSuccessListener(t -> callback.onSuccess(chatRoom))
                            .addOnFailureListener(callback::onFailure);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void listenChatRooms(IRepositoryCallback<List<ChatRoom>> cb) {
        db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .whereNotEqualTo("lastMessage", null)
                .orderBy("lastMessage.timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((q, err) -> {
                    if (err != null) {
                        cb.onFailure(err);
                        return;
                    }
                    assert q != null;

                    List<ChatRoom> chatRooms = new ArrayList<>();
                    List<Task<QuerySnapshot>> tasks = new ArrayList<>();

                    for (DocumentChange documentChange : q.getDocumentChanges()) {
                        DocumentSnapshot documentSnapshot = documentChange.getDocument();
                        ChatRoom chatRoom = documentSnapshot.toObject(ChatRoom.class);
                        chatRoom.setId(documentSnapshot.getId());

                        Task<QuerySnapshot> t = db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                                .document(chatRoom.getId())
                                .collection("members")
                                .get()
                                .addOnSuccessListener(memberDocs -> {
                                    Set<ChatRoom.Member> members = new HashSet<>();
                                    boolean found = false;
                                    for (DocumentSnapshot doc : memberDocs) {
                                        if (doc.getId().equals(signedInUser.getId())) {
                                            found = true;
                                        }
                                        ChatRoom.Member member = doc.toObject(ChatRoom.Member.class);
                                        assert member != null;
                                        member.setId(doc.getId());
                                        members.add(member);
                                    }
                                    if (found) {
                                        chatRoom.setMembers(members);
                                        chatRooms.add(chatRoom);
                                    }

                                })
                                .addOnFailureListener(cb::onFailure);
                        tasks.add(t);
                    }

                    Tasks.whenAllSuccess(tasks)
                            .addOnSuccessListener(success -> {
                                List<Task<DocumentSnapshot>> tasks2 = new ArrayList<>();

                                for (ChatRoom chatRoom : chatRooms) {
                                    for (ChatRoom.Member member : chatRoom.getMembers()) {
                                        Task<DocumentSnapshot> t = db.collection(Constants.USER_COLLECTION_NAME)
                                                .document(member.getId())
                                                .get()
                                                .addOnSuccessListener(userDoc -> {
                                                    User user = userDoc.toObject(User.class);
                                                    assert user != null;
                                                    user.setId(userDoc.getId());
                                                    member.setUser(user);
                                                })
                                                .addOnFailureListener(cb::onFailure);
                                        tasks2.add(t);
                                    }
                                }

                                Tasks.whenAllSuccess(tasks2)
                                        .addOnSuccessListener(t -> cb.onSuccess(chatRooms))
                                        .addOnFailureListener(cb::onFailure);
                            })
                            .addOnFailureListener(cb::onFailure);
                });
    }
}
