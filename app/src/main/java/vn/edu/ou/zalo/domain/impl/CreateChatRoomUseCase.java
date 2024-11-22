package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class CreateChatRoomUseCase {
    private final IChatRoomRepository chatRoomRepository;

    @Inject
    public CreateChatRoomUseCase(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public void execute(ChatRoom chatRoom, IDomainCallback<ChatRoom> cb) {
        chatRoomRepository.createChatRoom(chatRoom, new IRepositoryCallback<ChatRoom>() {
            @Override
            public void onSuccess(ChatRoom data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
