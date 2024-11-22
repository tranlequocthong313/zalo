package vn.edu.ou.zalo.data.repositories;

import java.util.List;

import vn.edu.ou.zalo.data.models.Message;

public interface IMessageRepository {
    void listenMessages(String chatRoomId, IRepositoryCallback<List<Message>> cb);
    void getMessages(String query, IRepositoryCallback<List<Message>> callback);

    void createMessage(Message message, IRepositoryCallback<Message> callback);
}
