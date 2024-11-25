package vn.edu.ou.zalo.domain.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class GetAddedFriendsUseCase {
    private final IFriendshipRepository friendshipRepository;

    @Inject
    public GetAddedFriendsUseCase(IFriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void execute(IDomainCallback<List<User>> cb) {
        friendshipRepository.getAddedFriends(new IRepositoryCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
