package vn.edu.ou.zalo.domain.impl;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Qualifier;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetUnimportantChatRoomsUseCaseImpl implements IGetListUseCase<ChatRoom> {
    IChatRoomRepository chatRoomRepository;

    @Inject
    public GetUnimportantChatRoomsUseCaseImpl(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public List<ChatRoom> execute() {
        Map<String, String> query = new HashMap<>();
        query.put("priority", ChatRoom.Priority.OTHER.name());
        return chatRoomRepository.getChatRooms(query);
    }
}
