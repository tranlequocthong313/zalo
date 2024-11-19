package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class DeleteFriendshipUseCase {
    private final IFriendshipRepository friendshipRepository;

    @Inject
    public DeleteFriendshipUseCase(IFriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void execute(String id, IDomainCallback<Void> cb) {
        friendshipRepository.deleteFriendship(id, new IRepositoryCallback<Void>() {
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
