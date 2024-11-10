package vn.edu.ou.zalo.data.repositories;

public interface IAuthRepository {
    void sendOtp(String phoneNumber, IRepositoryCallback<String> callback);

    void verifyOtp(String phoneNumber, String verificationId, IRepositoryCallback<Boolean> iRepositoryCallback);
}
