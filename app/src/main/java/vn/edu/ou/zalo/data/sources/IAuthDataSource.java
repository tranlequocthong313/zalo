package vn.edu.ou.zalo.data.sources;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IAuthDataSource {
    void sendOtp(String phoneNumber, IRepositoryCallback<String> callback);

    void verifyOtp(String verificationId, String otpCode, IRepositoryCallback<Boolean> callback);

    void checkPhoneNumberExists(String phoneNumber, IRepositoryCallback<Boolean> callback);

    void signInWithPhoneAndPassword(String phoneNumber, String password, IRepositoryCallback<Boolean> callback);

    User getSignedInUser();

    void signOut();
}
