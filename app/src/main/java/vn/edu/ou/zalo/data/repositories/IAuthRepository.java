package vn.edu.ou.zalo.data.repositories;

import vn.edu.ou.zalo.data.models.User;

public interface IAuthRepository {
    void sendOtp(String phoneNumber, IRepositoryCallback<String> callback);

    void verifyOtp(String phoneNumber, String verificationId, IRepositoryCallback<Boolean> iRepositoryCallback);

    void checkPhoneNumberExists(String phoneNumber, IRepositoryCallback<Boolean> callback);

    void loginWithPhoneAndPassword(String phoneNumber, String password, IRepositoryCallback<Boolean> callback);

    User getSignedInUser();

    void signOut();
}
