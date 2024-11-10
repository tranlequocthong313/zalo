package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IUserDataSource;
import vn.edu.ou.zalo.domain.ICreateUseCase;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class SignUpUseCase implements ICreateUseCase<User, Void> {
    private final IUserDataSource userDataSource;

    @Inject
    public SignUpUseCase(IUserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public void execute(User user, IDomainCallback<Void> callback) {
        userDataSource.createUser(user, new IRepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
