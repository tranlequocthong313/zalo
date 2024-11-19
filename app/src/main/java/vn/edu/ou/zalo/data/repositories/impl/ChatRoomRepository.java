package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;

public class ChatRoomRepository implements IChatRoomRepository {
    private final IChatRoomDataSource chatRoomDataSource;
    private final IAuthDataSource authDataSource;

    @Inject
    public ChatRoomRepository(IChatRoomDataSource chatRoomDataSource, IAuthDataSource authDataSource) {
        this.chatRoomDataSource = chatRoomDataSource;
        this.authDataSource = authDataSource;
    }

    @Override
    public void getChatRooms(Map<String, String> query, IRepositoryCallback<List<ChatRoom>> callback) {
        authDataSource.getSignedInUser(new IRepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                chatRoomDataSource.setLoginUser(data);
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
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void getChatRoom(User user, IRepositoryCallback<ChatRoom> callback) {
        chatRoomDataSource.getChatRoom(user, new IRepositoryCallback<ChatRoom>() {
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

    @Override
    public void checkEmptyChatRoom(IRepositoryCallback<Map<ChatRoom.Priority, Boolean>> callback) {
        authDataSource.getSignedInUser(new IRepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                chatRoomDataSource.setLoginUser(data);
                chatRoomDataSource.checkEmptyChatRoom(callback);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}