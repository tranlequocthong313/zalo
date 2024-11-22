package vn.edu.ou.zalo.domain.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class ListenChatRoomsUseCase {
    private final IChatRoomRepository chatRoomRepository;

    @Inject
    public ListenChatRoomsUseCase(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public void execute(IDomainCallback<List<ChatRoom>> cb) {
        chatRoomRepository.listenChatRooms(new IRepositoryCallback<List<ChatRoom>>() {
            @Override
            public void onSuccess(List<ChatRoom> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
