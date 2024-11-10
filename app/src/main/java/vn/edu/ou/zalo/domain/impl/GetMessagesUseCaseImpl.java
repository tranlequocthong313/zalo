package vn.edu.ou.zalo.domain.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetMessagesUseCaseImpl implements IGetListUseCase<Message> {
    private final IMessageRepository messageRepository;

    @Inject
    public GetMessagesUseCaseImpl(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void execute(Map<String, String> query, IDomainCallback<List<Message>> callback) {
        messageRepository.getMessages(query, new IRepositoryCallback<List<Message>>() {
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
