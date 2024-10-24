package vn.edu.ou.zalo.data.repositories;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;

public interface IChatRoomRepository {
    List<ChatRoom> getChatRooms();
}
