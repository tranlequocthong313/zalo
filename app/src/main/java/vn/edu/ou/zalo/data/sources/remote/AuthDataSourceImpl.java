package vn.edu.ou.zalo.data.sources.remote;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;

public class AuthDataSourceImpl implements IAuthDataSource {
    private static final String VN_PHONE_NUMBER_CODE = "+84";
    private final FirebaseAuth auth;

    @Inject
    public AuthDataSourceImpl() {
        // TODO: for test purpose right now, change the implementation after
        auth = FirebaseAuth.getInstance();
        auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
    }

    @Override
    public void sendOtp(String phoneNumber, IRepositoryCallback<String> callback) {
        // Format the phone number to the international format
        String formattedPhoneNumber = formatPhoneNumber(phoneNumber);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(formattedPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        // Auto-retrieval or instant verification succeeded
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        callback.onSuccess(verificationId);
                    }
                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private String formatPhoneNumber(String phoneNumber) {
        // Check if the phone number starts with '0' and replace it with '+84'
        if (phoneNumber.startsWith("0")) {
            return VN_PHONE_NUMBER_CODE + phoneNumber.substring(1);
        }
        return phoneNumber;  // Return as is if it's already in international format
    }

    public void verifyOtp(String verificationId, String otpCode, IRepositoryCallback<Boolean> callback) {
        // Create a PhoneAuthCredential with the verification ID and the OTP code
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpCode);

        // Sign in with the credential
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(true); // Verification success
                    } else {
                        callback.onFailure(task.getException()); // Verification failed
                    }
                });
    }
}
