package vn.edu.ou.zalo.data.repositories.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.repositories.IAuthRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;

public class AuthRepositoryImpl implements IAuthRepository {
    private final IAuthDataSource authDataSource;

    @Inject
    public AuthRepositoryImpl(IAuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    @Override
    public void sendOtp(String phoneNumber, IRepositoryCallback<String> callback) {
        authDataSource.sendOtp(phoneNumber, new IRepositoryCallback<String>() {
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

    @Override
    public void verifyOtp(String verificationId, String otpCode, IRepositoryCallback<Boolean> repositoryCallback) {
        authDataSource.verifyOtp(verificationId, otpCode, repositoryCallback);
    }
}
