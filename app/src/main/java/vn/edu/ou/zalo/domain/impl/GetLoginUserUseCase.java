package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;

public class GetLoginUserUseCase implements IGetDetailUseCase<User> {
    IUserRepository userRepository;

    @Inject
    public GetLoginUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute() {
        return userRepository.getLoginUser();
    }
}
