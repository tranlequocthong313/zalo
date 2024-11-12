package vn.edu.ou.zalo.data.sources;

import java.util.List;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IFriendshipDataSource {
    void addFriend(User friend, IRepositoryCallback<Friendship> cb);

    void checkAddedFriend(User user, IRepositoryCallback<Boolean> cb);

    void getAddedFriends(IRepositoryCallback<List<User>> cb);

    void setSignedInUser(User user);

    void getRecommendedFriends(IRepositoryCallback<List<User>> cb);
}
