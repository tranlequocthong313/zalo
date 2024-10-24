package vn.edu.ou.zalo.data.repositories.impl;

import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;

public class ChatRoomRepositoryImpl implements IChatRoomRepository {
    private final IChatRoomDataSource chatRoomDataSource;

    @Inject
    public ChatRoomRepositoryImpl(IChatRoomDataSource chatRoomDataSource) {
        this.chatRoomDataSource = chatRoomDataSource;
    }

    @Override
    public List<ChatRoom> getChatRooms(Map<String, String> query) {
        return chatRoomDataSource.getChatRooms(query);
    }
}