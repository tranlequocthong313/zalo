package vn.edu.ou.zalo.data.repositories.impl;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;

public class ChatRoomRepositoryImpl implements IChatRoomRepository {
    private static final String TAG = "ChatRoomRepositoryImpl";

    private final IChatRoomDataSource chatRoomDataSource;

    @Inject
    public ChatRoomRepositoryImpl(IChatRoomDataSource chatRoomDataSource) {
        this.chatRoomDataSource = chatRoomDataSource;
    }

    @Override
    public List<ChatRoom> getChatRooms() {
        Log.i(TAG, "Fetch Chat Rooms");
        return chatRoomDataSource.getChatRooms();
    }
}