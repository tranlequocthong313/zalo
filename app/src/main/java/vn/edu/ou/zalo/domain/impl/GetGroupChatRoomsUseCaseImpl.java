package vn.edu.ou.zalo.domain.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetGroupChatRoomsUseCaseImpl implements IGetListUseCase<ChatRoom> {
    IChatRoomRepository chatRoomRepository;

    @Inject
    public GetGroupChatRoomsUseCaseImpl(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public List<ChatRoom> execute() {
        Map<String, String> query = new HashMap<>();
        query.put("type", ChatRoom.Type.GROUP.name());
        return chatRoomRepository.getChatRooms(query);
    }
}
