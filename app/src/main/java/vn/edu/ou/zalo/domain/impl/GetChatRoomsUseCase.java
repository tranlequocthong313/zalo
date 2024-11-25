package vn.edu.ou.zalo.domain.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class GetChatRoomsUseCase {
    IChatRoomRepository chatRoomRepository;

    @Inject
    public GetChatRoomsUseCase(IChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public void execute(Map<String, String> query, IDomainCallback<List<ChatRoom>> callback) {
        chatRoomRepository.getChatRooms(query, new IRepositoryCallback<List<ChatRoom>>() {
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
}
