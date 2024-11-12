package vn.edu.ou.zalo.domain.impl;

import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class CheckAddedFriendUseCase {
    private final IFriendshipRepository friendshipRepository;

    @Inject
    public CheckAddedFriendUseCase(IFriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void execute(User user, IDomainCallback<Boolean> cb) {
        friendshipRepository.checkAddedFriend(user, new IRepositoryCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
