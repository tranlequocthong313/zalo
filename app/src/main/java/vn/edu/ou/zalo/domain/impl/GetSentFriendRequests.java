package vn.edu.ou.zalo.domain.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class GetSentFriendRequests {
    private final IFriendshipRepository friendshipRepository;

    @Inject
    public GetSentFriendRequests(IFriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void execute(IDomainCallback<List<Friendship>> cb) {
        friendshipRepository.getSentFriendRequests(new IRepositoryCallback<List<Friendship>>() {
            @Override
            public void onSuccess(List<Friendship> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
