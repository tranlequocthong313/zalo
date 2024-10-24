package vn.edu.ou.zalo.data.sources;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;

public interface IChatRoomDataSource {
    List<ChatRoom> getChatRooms();
}
