package vn.edu.ou.zalo.domain.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class GetFriendRecommendations {
    private final IFriendshipRepository friendshipRepository;

    @Inject
    public GetFriendRecommendations(IFriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void execute(IDomainCallback<List<User>> callback) {
        friendshipRepository.getRecommendedFriends(new IRepositoryCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
