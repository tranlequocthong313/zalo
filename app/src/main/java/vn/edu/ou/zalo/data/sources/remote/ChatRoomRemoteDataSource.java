package vn.edu.ou.zalo.data.sources.remote;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.utils.Constants;

public class ChatRoomRemoteDataSource implements IChatRoomDataSource {
    private User loginUser;
    private final FirebaseFirestore db;

    @Inject
    public ChatRoomRemoteDataSource(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public void getChatRooms(Map<String, String> query, IRepositoryCallback<List<ChatRoom>> callback) {
        Query chatRoomCollection = db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .whereArrayContains("members", loginUser.getId());

        if (query != null && query.containsKey("priority") && query.get("priority") != null) {
            ChatRoom.Priority priority = ChatRoom.Priority.valueOf(query.get("priority"));
            chatRoomCollection = chatRoomCollection.whereEqualTo("priority", priority.name());
        }
        if (query != null && query.containsKey("type") && query.get("type") != null) {
            ChatRoom.Type type = ChatRoom.Type.valueOf(query.get("type"));
            chatRoomCollection = chatRoomCollection.whereEqualTo("type", type.name());
        }

        chatRoomCollection.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful() || task.getResult() == null) {
                callback.onFailure(task.getException());
                return;
            }
            List<ChatRoom> chatRooms = new ArrayList<>();
            for (DocumentSnapshot document : task.getResult()) {
                ChatRoom chatRoom = document.toObject(ChatRoom.class);
                chatRooms.add(chatRoom);
            }
            callback.onSuccess(chatRooms);
        });
    }

    @Override
    public void getChatRoom(String id, IRepositoryCallback<ChatRoom> callback) {
        db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful() || task.getResult() == null) {
                        callback.onFailure(task.getException());
                        return;
                    }
                    ChatRoom room = task.getResult().toObject(ChatRoom.class);
                    callback.onSuccess(room);
                });
    }

    @Override
    public void getChatRoom(User user, IRepositoryCallback<ChatRoom> callback) {
        db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .whereEqualTo("type", ChatRoom.Type.SINGLE.name())
                .get()
                .addOnSuccessListener(chatRoomDocs -> {
                    for (DocumentSnapshot chatRoomDoc : chatRoomDocs.getDocuments()) {
                        String chatRoomId = chatRoomDoc.getId();

                        db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                                .document(chatRoomId)
                                .collection("members")
                                .get()
                                .addOnSuccessListener(memberDocs -> {
                                    List<String> memberIds = new ArrayList<>();
                                    for (DocumentSnapshot memberDoc : memberDocs.getDocuments()) {
                                        memberIds.add(memberDoc.getId());
                                    }

                                    if (memberIds.size() == 2 && memberIds.contains(user.getId()) && memberIds.contains(loginUser.getId())) {
                                        ChatRoom chatRoom = chatRoomDoc.toObject(ChatRoom.class);
                                        if (chatRoom != null) {
                                            Set<ChatRoom.Member> members = new HashSet<>();
                                            List<Task<DocumentSnapshot>> userTasks = new ArrayList<>();

                                            for (DocumentSnapshot doc : memberDocs.getDocuments()) {
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

                                            Tasks.whenAllComplete(userTasks)
                                                    .addOnSuccessListener(tasks -> {
                                                        chatRoom.setMembers(members);
                                                        callback.onSuccess(chatRoom);
                                                    })
                                                    .addOnFailureListener(callback::onFailure);
                                        }
                                    }
                                })
                                .addOnFailureListener(callback::onFailure);
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }


    @Override
    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }


    @Override
    public void checkEmptyChatRoom(IRepositoryCallback<Map<ChatRoom.Priority, Boolean>> callback) {
        Map<ChatRoom.Priority, Boolean> isEmpty = new HashMap<>();

        db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .whereArrayContains("members", loginUser.getId())
                .whereEqualTo("priority", ChatRoom.Priority.FOCUSED.name())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        isEmpty.put(ChatRoom.Priority.FOCUSED, task.getResult().isEmpty());
                    } else {
                        callback.onFailure(task.getException());
                        return;
                    }

                    db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                            .whereArrayContains("members", loginUser.getId())
                            .whereEqualTo("priority", ChatRoom.Priority.OTHER.name())
                            .get()
                            .addOnCompleteListener(otherTask -> {
                                if (otherTask.isSuccessful()) {
                                    isEmpty.put(ChatRoom.Priority.OTHER, otherTask.getResult().isEmpty());
                                    callback.onSuccess(isEmpty);
                                } else {
                                    callback.onFailure(otherTask.getException());
                                }
                            });
                });
    }
}
