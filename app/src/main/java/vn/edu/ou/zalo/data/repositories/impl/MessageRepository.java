package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IMessageDataSource;

public class MessageRepository implements IMessageRepository {
    private final IMessageDataSource messageDataSource;

    @Inject
    public MessageRepository(IMessageDataSource messageDataSource) {
        this.messageDataSource = messageDataSource;
    }

    @Override
    public void listenMessages(String chatRoomId, IRepositoryCallback<List<Message>> cb) {
        messageDataSource.listenMessages(chatRoomId, cb);
    }

    @Override
    public void getMessages(String chatRoomId, IRepositoryCallback<List<Message>> callback) {
        messageDataSource.getMessages(chatRoomId, callback);
    }

    @Override
    public void createMessage(Message message, IRepositoryCallback<Message> callback) {
        messageDataSource.createMessage(message, callback);
    }
}
