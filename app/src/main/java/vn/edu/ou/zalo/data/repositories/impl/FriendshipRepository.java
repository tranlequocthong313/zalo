package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;
import vn.edu.ou.zalo.data.sources.IFriendshipDataSource;

public class FriendshipRepository implements IFriendshipRepository {
    private final IFriendshipDataSource friendshipDataSource;
    private User signedInUser;

    @Inject
    public FriendshipRepository(IFriendshipDataSource friendshipDataSource, IAuthDataSource authDataSource) {
        this.friendshipDataSource = friendshipDataSource;
        authDataSource.getSignedInUser(new IRepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                friendshipDataSource.setSignedInUser(data);
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    @Override
    public void addFriend(User friend, IRepositoryCallback<Friendship> cb) {
        friendshipDataSource.addFriend(friend, cb);
    }

    @Override
    public void getRecommendedFriends(IRepositoryCallback<List<User>> cb) {
        friendshipDataSource.getRecommendedFriends(cb);
    }

    @Override
    public void getAddedFriends(IRepositoryCallback<List<User>> cb) {
        friendshipDataSource.getAddedFriends(cb);
    }

    @Override
    public void checkFriendStatus(User user, IRepositoryCallback<Friendship.Status> callback) {
        friendshipDataSource.checkFriendStatus(user, callback);
    }
}
