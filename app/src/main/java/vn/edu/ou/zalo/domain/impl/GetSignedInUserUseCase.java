package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IAuthRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class GetSignedInUserUseCase {
    IAuthRepository authRepository;

    @Inject
    public GetSignedInUserUseCase(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(IDomainCallback<User> cb) {
        authRepository.getSignedInUser(new IRepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
