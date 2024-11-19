package vn.edu.ou.zalo.data.sources;

import java.util.List;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IFriendshipDataSource {
    void addFriend(User friend, IRepositoryCallback<Friendship> cb);

    void checkFriendStatus(User user, IRepositoryCallback<Friendship.Status> cb);

    void getAddedFriends(IRepositoryCallback<List<User>> cb);

    void setSignedInUser(User user);

    void getRecommendedFriends(IRepositoryCallback<List<User>> cb);
}
