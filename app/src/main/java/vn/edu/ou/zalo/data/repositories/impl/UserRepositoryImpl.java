package vn.edu.ou.zalo.data.repositories.impl;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;

public class UserRepositoryImpl implements IUserRepository {
    private final IUserDataSource userDataSource;

    @Inject
    public UserRepositoryImpl(IUserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public List<User> getUsers() {
        return userDataSource.getUsers();
    }

    @Override
    public User getLoginUser() {
        return userDataSource.getLoginUser();
    }
}