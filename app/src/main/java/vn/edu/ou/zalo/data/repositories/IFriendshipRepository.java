package vn.edu.ou.zalo.data.repositories;

import java.util.List;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;

public interface IFriendshipRepository {
    void addFriend(User friend, IRepositoryCallback<Friendship> cb);

    void getRecommendedFriends(IRepositoryCallback<List<User>> cb);

    void getAddedFriends(IRepositoryCallback<List<User>> cb);

    void checkFriendStatus(User user, IRepositoryCallback<Friendship.Status> iRepositoryCallback);
}
