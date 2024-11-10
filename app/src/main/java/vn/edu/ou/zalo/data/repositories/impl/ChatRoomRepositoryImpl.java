package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;

public class ChatRoomRepositoryImpl implements IChatRoomRepository {
    private final IChatRoomDataSource chatRoomDataSource;
    private final IAuthDataSource authDataSource;

    @Inject
    public ChatRoomRepositoryImpl(IChatRoomDataSource chatRoomDataSource, IAuthDataSource authDataSource) {
        this.chatRoomDataSource = chatRoomDataSource;
        this.authDataSource = authDataSource;
    }

    @Override
    public void getChatRooms(Map<String, String> query, IRepositoryCallback<List<ChatRoom>> callback) {
        chatRoomDataSource.setLoginUser(authDataSource.getSignedInUser());
        chatRoomDataSource.getChatRooms(query, new IRepositoryCallback<List<ChatRoom>>() {
            @Override
            public void onSuccess(List<ChatRoom> data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void getChatRoom(String id, IRepositoryCallback<ChatRoom> callback) {
        chatRoomDataSource.getChatRoom(id, new IRepositoryCallback<ChatRoom>() {
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