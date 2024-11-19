package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class GetDetailChatRoomByUserUseCase {
    IChatRoomRepository chatRoomRepository;

    @Inject
    public GetDetailChatRoomByUserUseCase(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public void execute(User user, IDomainCallback<ChatRoom> callback) {
        chatRoomRepository.getChatRoom(user, new IRepositoryCallback<ChatRoom>() {
            @Override
            public void onSuccess(ChatRoom data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
