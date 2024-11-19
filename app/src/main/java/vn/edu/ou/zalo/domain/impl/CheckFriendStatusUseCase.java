package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class CheckFriendStatusUseCase {
    private final IFriendshipRepository friendshipRepository;

    @Inject
    public CheckFriendStatusUseCase(IFriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void execute(User user, IDomainCallback<Friendship.Status> cb) {
        friendshipRepository.checkFriendStatus(user, new IRepositoryCallback<Friendship.Status>() {
            @Override
            public void onSuccess(Friendship.Status data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
