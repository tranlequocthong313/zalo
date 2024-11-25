package vn.edu.ou.zalo.data.repositories;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;

public interface IChatRoomRepository {
    void getChatRooms(Map<String, String> query, IRepositoryCallback<List<ChatRoom>> callback);

    void getChatRoom(User user, IRepositoryCallback<ChatRoom> callback);

    void getChatRoom(String id, IRepositoryCallback<ChatRoom> callback);

    void checkEmptyChatRoom(IRepositoryCallback<Map<ChatRoom.Priority, Boolean>> callback);

    void createChatRoom(ChatRoom chatRoom, IRepositoryCallback<ChatRoom> cb);

    void listenChatRooms(IRepositoryCallback<List<ChatRoom>> iRepositoryCallback);
}
