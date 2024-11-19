package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.ICreateUseCase;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class SendFriendRequestUseCase implements ICreateUseCase<User, Friendship> {
    private final IFriendshipRepository friendshipRepository;

    @Inject
    public SendFriendRequestUseCase(IFriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public void execute(User user, IDomainCallback<Friendship> callback) {
        friendshipRepository.addFriend(user, new IRepositoryCallback<Friendship>() {
            @Override
            public void onSuccess(Friendship data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
