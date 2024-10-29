package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;

public class GetDetailChatRoomUseCaseImpl implements IGetDetailUseCase<ChatRoom> {
    IChatRoomRepository chatRoomRepository;

    @Inject
    public GetDetailChatRoomUseCaseImpl(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public ChatRoom execute(String id) {
        return chatRoomRepository.getChatRoom(id);
    }
}
