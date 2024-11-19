package vn.edu.ou.zalo.domain.impl;

import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class CheckEmptyChatRoomUseCase {
    private final IChatRoomRepository chatRoomRepository;

    @Inject
    public CheckEmptyChatRoomUseCase(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public void execute(IDomainCallback<Map<ChatRoom.Priority, Boolean>> cb) {
        chatRoomRepository.checkEmptyChatRoom(new IRepositoryCallback<Map<ChatRoom.Priority, Boolean>>() {
            @Override
            public void onSuccess(Map<ChatRoom.Priority, Boolean> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
