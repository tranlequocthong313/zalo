package vn.edu.ou.zalo.data.sources.remote;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.utils.Constants;

public class ChatRoomRemoteDataSourceImpl implements IChatRoomDataSource {
    private User loginUser;
    private final FirebaseFirestore db;

    @Inject
    public ChatRoomRemoteDataSourceImpl() {
        this.db = FirebaseFirestore.getInstance();
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
    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }
}
