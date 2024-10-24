package vn.edu.ou.zalo.domain.impl;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetChatRoomsUseCaseImpl implements IGetListUseCase<ChatRoom> {
    private static final String TAG = "GetChatRoomsUseCaseImpl";

    IChatRoomRepository chatRoomRepository;

    @Inject
    public GetChatRoomsUseCaseImpl(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public List<ChatRoom> execute() {
        Log.i(TAG, "Fetch Chat Rooms");
        return chatRoomRepository.getChatRooms();
    }
}
