package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.repositories.IAuthRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class SendOtpUseCase {
    private final IAuthRepository authRepository;

    @Inject
    public SendOtpUseCase(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String phoneNumber, IDomainCallback<String> callback) {
        authRepository.sendOtp(phoneNumber, new IRepositoryCallback<String>() {
            @Override
            public void onSuccess(String data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
