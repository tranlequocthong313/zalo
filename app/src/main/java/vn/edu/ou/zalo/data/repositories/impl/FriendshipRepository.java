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
        authDataSource.getSignedInUser(new IRepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                friendshipDataSource.setSignedInUser(data);
                friendshipDataSource.getAddedFriends(cb);
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    @Override
    public void checkFriendStatus(User user, IRepositoryCallback<Friendship.Status> callback) {
        friendshipDataSource.checkFriendStatus(user, callback);
    }

    @Override
    public void getReceivedFriendRequests(IRepositoryCallback<List<Friendship>> cb) {
        friendshipDataSource.getFriendRequests(true, cb);
    }

    @Override
    public void getSentFriendRequests(IRepositoryCallback<List<Friendship>> cb) {
        friendshipDataSource.getFriendRequests(false, cb);
    }

    @Override
    public void updateFriendshipStatus(Friendship friendship, IRepositoryCallback<Void> cb) {
        friendshipDataSource.updateFriendshipStatus(friendship, cb);
    }

    @Override
    public void deleteFriendship(String id, IRepositoryCallback<Void> cb) {
        friendshipDataSource.deleteFriendship(id, cb);
    }
}
