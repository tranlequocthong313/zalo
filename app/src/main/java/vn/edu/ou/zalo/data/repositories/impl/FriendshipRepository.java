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
    private final IAuthDataSource authDataSource;

    @Inject
    public FriendshipRepository(IFriendshipDataSource friendshipDataSource, IAuthDataSource authDataSource) {
        this.friendshipDataSource = friendshipDataSource;
        this.authDataSource = authDataSource;
    }

    @Override
    public void addFriend(User friend, IRepositoryCallback<Friendship> cb) {
        this.friendshipDataSource.setSignedInUser(authDataSource.getSignedInUser());
        friendshipDataSource.addFriend(friend, cb);
    }

    @Override
    public void getRecommendedFriends(IRepositoryCallback<List<User>> cb) {
        this.friendshipDataSource.setSignedInUser(authDataSource.getSignedInUser());
        friendshipDataSource.getRecommendedFriends(cb);
    }

    @Override
    public void checkAddedFriend(User user, IRepositoryCallback<Boolean> cb) {
        this.friendshipDataSource.setSignedInUser(authDataSource.getSignedInUser());
        friendshipDataSource.checkAddedFriend(user, cb);
    }
    
    @Override
    public void getAddedFriends(IRepositoryCallback<List<User>> cb) {
        this.friendshipDataSource.setSignedInUser(authDataSource.getSignedInUser());
        friendshipDataSource.getAddedFriends(cb);
    }
}
