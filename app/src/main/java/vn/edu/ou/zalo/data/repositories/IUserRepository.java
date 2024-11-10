package vn.edu.ou.zalo.data.repositories;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.User;

public interface IUserRepository {
    default void getUsers(IRepositoryCallback<List<User>> callback) {
        getUsers(null, callback);
    }

    void getUsers(Map<String, String> query, IRepositoryCallback<List<User>> callback);

    User getLoginUser();
}
