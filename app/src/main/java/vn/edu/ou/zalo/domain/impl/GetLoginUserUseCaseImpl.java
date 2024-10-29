package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;

public class GetLoginUserUseCaseImpl implements IGetDetailUseCase<User> {
    IUserRepository userRepository;

    @Inject
    public GetLoginUserUseCaseImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(String id) {
        return userRepository.getLoginUser();
    }
}
