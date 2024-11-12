package vn.edu.ou.zalo.data.repositories.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IAuthRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;

public class AuthRepository implements IAuthRepository {
    private final IAuthDataSource authDataSource;

    @Inject
    public AuthRepository(IAuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    @Override
    public void sendOtp(String phoneNumber, IRepositoryCallback<String> callback) {
        checkPhoneNumberExists(phoneNumber, new IRepositoryCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean exists) {
                if (exists) {
                    callback.onFailure(new Exception("Phone number already registered."));
                } else {
                    authDataSource.sendOtp(phoneNumber, callback);
                }
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

    @Override
    public void checkPhoneNumberExists(String phoneNumber, IRepositoryCallback<Boolean> callback) {
        authDataSource.checkPhoneNumberExists(phoneNumber, callback);
    }

    @Override
    public void loginWithPhoneAndPassword(String phoneNumber, String password, IRepositoryCallback<Boolean> callback) {
        authDataSource.signInWithPhoneAndPassword(phoneNumber, password, callback);
    }

    @Override
    public User getSignedInUser() {
        return authDataSource.getSignedInUser();
    }

    @Override
    public void signOut() {
        authDataSource.signOut();
    }
}
