package vn.edu.ou.zalo.domain.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetSortedFriendsImpl implements IGetListUseCase<User> {

    IUserRepository userRepository;

    @Inject
    public GetSortedFriendsImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> execute() {
        List<User> users = new ArrayList<>(userRepository.getUsers());
        users.sort(Comparator.comparing(User::getFullName));
        return users;
    }

    @Override
    public List<User> execute(Map<String, String> query) {
        return userRepository.getUsers();
    }
}
