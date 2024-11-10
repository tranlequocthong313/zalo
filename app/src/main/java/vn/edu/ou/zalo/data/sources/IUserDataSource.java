package vn.edu.ou.zalo.data.sources;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IUserDataSource {
    void getUsers(IRepositoryCallback<List<User>> callback);

    void createUser(User user, IRepositoryCallback<Void> callback);
}
