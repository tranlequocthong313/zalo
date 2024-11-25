package vn.edu.ou.zalo.domain.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class SearchUserUseCase {
    private final IUserRepository userRepository;

    @Inject
    public SearchUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Map<String, String> query, IDomainCallback<List<User>> callback) {
        userRepository.getUsers(query, new IRepositoryCallback<List<User>>() {

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
}
