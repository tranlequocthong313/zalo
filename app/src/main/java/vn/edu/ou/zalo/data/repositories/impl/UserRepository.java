package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;

public class UserRepository implements IUserRepository {
    private final IUserDataSource userDataSource;
    private final IAuthDataSource authDataSource;

    @Inject
    public UserRepository(IUserDataSource userDataSource, IAuthDataSource authDataSource) {
        this.userDataSource = userDataSource;
        this.authDataSource = authDataSource;
    }

    @Override
    public void getUsers(Map<String, String> query, IRepositoryCallback<List<User>> callback) {
        authDataSource.getSignedInUser(new IRepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                userDataSource.getUsers(query, data, new IRepositoryCallback<List<User>>() {
                    @Override
                    public void onSuccess(List<User> data) {
                        callback.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void createUser(User user, IRepositoryCallback<Void> callback) {
        userDataSource.createUser(user, callback);
    }
}