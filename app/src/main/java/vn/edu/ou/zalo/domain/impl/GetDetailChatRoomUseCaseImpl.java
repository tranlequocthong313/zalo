package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;

public class GetDetailChatRoomUseCaseImpl implements IGetDetailUseCase<ChatRoom> {
    IChatRoomRepository chatRoomRepository;

    @Inject
    public GetDetailChatRoomUseCaseImpl(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public void execute(String id, IDomainCallback<ChatRoom> callback) {
        chatRoomRepository.getChatRoom(id, new IRepositoryCallback<ChatRoom>() {
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
