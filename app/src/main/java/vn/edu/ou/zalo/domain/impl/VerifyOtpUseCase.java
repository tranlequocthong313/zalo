package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.repositories.IAuthRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class VerifyOtpUseCase {
    private final IAuthRepository authRepository;

    @Inject
    public VerifyOtpUseCase(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String phoneNumber, String verificationId, IDomainCallback<Boolean> callback) {
        authRepository.verifyOtp(phoneNumber, verificationId, new IRepositoryCallback<Boolean>() {
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
