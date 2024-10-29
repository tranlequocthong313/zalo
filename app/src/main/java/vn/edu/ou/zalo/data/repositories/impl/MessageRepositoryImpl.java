package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.data.sources.IMessageDataSource;

public class MessageRepositoryImpl implements IMessageRepository {
    private final IMessageDataSource messageDataSource;

    @Inject
    public MessageRepositoryImpl(IMessageDataSource messageDataSource) {
        this.messageDataSource = messageDataSource;
    }

    @Override
    public List<Message> getMessages(Map<String, String> query) {
        return messageDataSource.getMessages(query);
    }
}
