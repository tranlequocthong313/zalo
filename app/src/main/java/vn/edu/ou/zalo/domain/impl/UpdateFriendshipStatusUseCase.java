package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class UpdateFriendshipStatusUseCase {
    private final IFriendshipRepository friendshipRepository;

    @Inject
    public UpdateFriendshipStatusUseCase(IFriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void execute(Friendship friendship, IDomainCallback<Void> cb) {
        friendshipRepository.updateFriendshipStatus(friendship, new IRepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
