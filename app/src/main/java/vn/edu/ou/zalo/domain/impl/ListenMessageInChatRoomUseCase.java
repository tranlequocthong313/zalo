package vn.edu.ou.zalo.domain.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class ListenMessageInChatRoomUseCase {
    private final IMessageRepository messageRepository;

    @Inject
    public ListenMessageInChatRoomUseCase(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void execute(String chatRoomId, IDomainCallback<List<Message>> cb) {
        messageRepository.listenMessages(chatRoomId, new IRepositoryCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
