package vn.edu.ou.zalo.data.sources;

import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IAuthDataSource {
    void sendOtp(String phoneNumber, IRepositoryCallback<String> callback);

    void verifyOtp(String verificationId, String otpCode, IRepositoryCallback<Boolean> callback);
}
