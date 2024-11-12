package vn.edu.ou.zalo.data.sources;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IUserDataSource {
    default void getUsers(IRepositoryCallback<List<User>> callback) {
        getUsers(null, null, callback);
    }

    default void getUsers(Map<String, String> query, IRepositoryCallback<List<User>> callback) {
        getUsers(query, null, callback);
    }

    void getUsers(Map<String, String> query, User signedInUser, IRepositoryCallback<List<User>> callback);

    void createUser(User user, IRepositoryCallback<Void> callback);
}
