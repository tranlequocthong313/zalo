package vn.edu.ou.zalo.data.repositories;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.ChatRoom;

public interface IChatRoomRepository {
    default List<ChatRoom> getChatRooms() {
        return getChatRooms(null);
    }

    List<ChatRoom> getChatRooms(Map<String, String> query);
}
