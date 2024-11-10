package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IMessageDataSource;

public class MessageRepositoryImpl implements IMessageRepository {
    private final IMessageDataSource messageDataSource;

    @Inject
    public MessageRepositoryImpl(IMessageDataSource messageDataSource) {
        this.messageDataSource = messageDataSource;
    }

    @Override
    public void getMessages(Map<String, String> query, IRepositoryCallback<List<Message>> callback) {
        messageDataSource.getMessages(query, new IRepositoryCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
