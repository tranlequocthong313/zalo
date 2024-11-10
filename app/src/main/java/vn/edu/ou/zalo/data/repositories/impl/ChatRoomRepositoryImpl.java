package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;
import vn.edu.ou.zalo.di.qualifiers.Fake;

public class ChatRoomRepositoryImpl implements IChatRoomRepository {
    private final IChatRoomDataSource chatRoomDataSource;
    private final IUserDataSource userDataSource;

    @Inject
    public ChatRoomRepositoryImpl(@Fake IChatRoomDataSource chatRoomDataSource, @Fake IUserDataSource userDataSource) {
        this.chatRoomDataSource = chatRoomDataSource;
        this.userDataSource = userDataSource;
    }

    @Override
    public void getChatRooms(Map<String, String> query, IRepositoryCallback<List<ChatRoom>> callback) {
        chatRoomDataSource.setLoginUser(userDataSource.getLoginUser());
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