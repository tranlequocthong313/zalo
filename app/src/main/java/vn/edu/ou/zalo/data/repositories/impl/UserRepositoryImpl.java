package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IUserDataSource;
import vn.edu.ou.zalo.di.qualifiers.Fake;

public class UserRepositoryImpl implements IUserRepository {
    private final IUserDataSource userDataSource;

    @Inject
    public UserRepositoryImpl(@Fake IUserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public void getUsers(Map<String, String> query, IRepositoryCallback<List<User>> callback) {
        userDataSource.getUsers(new IRepositoryCallback<List<User>>() {
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
    public User getLoginUser() {
        return userDataSource.getLoginUser();
    }
}