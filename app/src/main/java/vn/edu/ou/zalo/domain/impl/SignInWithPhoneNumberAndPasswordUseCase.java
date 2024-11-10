package vn.edu.ou.zalo.domain.impl;

import android.util.Log;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.repositories.IAuthRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class SignInWithPhoneNumberAndPasswordUseCase {
    private final IAuthRepository authRepository;

    @Inject
    public SignInWithPhoneNumberAndPasswordUseCase(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String phoneNumber, String password, IDomainCallback<Boolean> callback) {
        authRepository.loginWithPhoneAndPassword(phoneNumber, password, new IRepositoryCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
