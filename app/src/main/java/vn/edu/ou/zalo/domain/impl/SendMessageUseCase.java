package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class SendMessageUseCase {
    private final IMessageRepository messageRepository;

    @Inject
    public SendMessageUseCase(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void execute(Message message, IDomainCallback<Message> cb) {
        messageRepository.createMessage(message, new IRepositoryCallback<Message>() {
            @Override
            public void onSuccess(Message data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
