package vn.edu.ou.zalo.data.sources;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IChatRoomDataSource {

    void getChatRooms(Map<String, String> query, IRepositoryCallback<List<ChatRoom>> callback);

    void getChatRoom(String id, IRepositoryCallback<ChatRoom> callback);

    void setLoginUser(User loginUser);
}
