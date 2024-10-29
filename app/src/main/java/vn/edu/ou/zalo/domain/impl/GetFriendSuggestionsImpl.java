package vn.edu.ou.zalo.domain.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetFriendSuggestionsImpl implements IGetListUseCase<User> {

    IUserRepository userRepository;

    @Inject
    public GetFriendSuggestionsImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> execute() {
        return userRepository.getUsers();
    }

    @Override
    public List<User> execute(Map<String, String> query) {
        return userRepository.getUsers();
    }
}
