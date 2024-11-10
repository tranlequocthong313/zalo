package vn.edu.ou.zalo.data.repositories;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.ChatRoom;

public interface IChatRoomRepository {
    void getChatRooms(Map<String, String> query, IRepositoryCallback<List<ChatRoom>> callback);

    void getChatRoom(String id, IRepositoryCallback<ChatRoom> callback);
}
