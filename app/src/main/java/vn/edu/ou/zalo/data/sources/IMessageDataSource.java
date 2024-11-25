package vn.edu.ou.zalo.data.sources;

import java.util.List;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IMessageDataSource {
    void listenMessages(String chatRoomId, IRepositoryCallback<List<Message>> cb);

    void getMessages(String chatRoomId, IRepositoryCallback<List<Message>> dataSourceCallback);

    void createMessage(Message message, IRepositoryCallback<Message> cb);
}
