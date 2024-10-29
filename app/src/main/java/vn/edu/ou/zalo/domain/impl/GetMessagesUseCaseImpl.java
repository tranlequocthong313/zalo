package vn.edu.ou.zalo.domain.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetMessagesUseCaseImpl implements IGetListUseCase<Message> {
    private final IMessageRepository messageRepository;

    @Inject
    public GetMessagesUseCaseImpl(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> execute(Map<String, String> query) {
        return messageRepository.getMessages(query);
    }
}
