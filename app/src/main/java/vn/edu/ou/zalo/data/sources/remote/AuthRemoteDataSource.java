package vn.edu.ou.zalo.data.sources.remote;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;
import vn.edu.ou.zalo.utils.Constants;
import vn.edu.ou.zalo.utils.Json;

public class AuthRemoteDataSource implements IAuthDataSource {
    private static final String VN_PHONE_NUMBER_CODE = "+84";
    private final FirebaseAuth auth;
    private final FirebaseFirestore db;
    private final FirebaseFunctions func;

    @Inject
    public AuthRemoteDataSource(FirebaseAuth auth, FirebaseFirestore db, FirebaseFunctions func) {
        this.auth = auth;
        this.db = db;
        this.func = func;

        // TODO: for test purpose right now, change the implementation after
        auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
    }

    @Override
    public void sendOtp(String phoneNumber, IRepositoryCallback<String> callback) {
        // Format the phone number to the international format
        String formattedPhoneNumber = formatPhoneNumber(phoneNumber);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(formattedPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        // Auto-retrieval or instant verification succeeded
                        auth.signInWithCredential(credential);
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

    @Override
    public void checkPhoneNumberExists(String phoneNumber, IRepositoryCallback<Boolean> callback) {
        db.collection(Constants.USER_COLLECTION_NAME)
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        callback.onSuccess(true);
                    } else {
                        callback.onSuccess(false);
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    public void signInWithPhoneAndPassword(String phoneNumber, String password, IRepositoryCallback<Boolean> callback) {
        // Create the data for Firebase Function
        Map<String, String> data = new HashMap<>();
        data.put("phoneNumber", phoneNumber);
        data.put("password", password);

        // Call Firebase Function to validate login credentials
        func.getHttpsCallable("signInWithPhoneNumberAndPassword") // The Firebase function name
                .call(data)
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        return Json.getStringObjectMap(task.getResult().getData());
                    } else {
                        throw Objects.requireNonNull(task.getException()); // If the Firebase function fails, throw the exception
                    }
                })
                .addOnSuccessListener(responseData -> {
                    // Get the custom token from Firebase Function's result
                    String token = (String) responseData.get("token");

                    if (token != null) {
                        // Sign in with custom token returned by the function
                        auth.signInWithCustomToken(token)
                                .addOnCompleteListener(signInTask -> {
                                    if (signInTask.isSuccessful()) {
                                        callback.onSuccess(true);
                                    } else {
                                        // Sign-in failed
                                        callback.onFailure(signInTask.getException());
                                    }
                                });
                    } else {
                        // No token returned, authentication failed
                        callback.onFailure(new Exception("Authentication failed: No token returned"));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void getSignedInUser(IRepositoryCallback<User> callback) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            callback.onFailure(new Exception("User is not signed in"));
            return;
        }
        if (firebaseUser.getPhoneNumber() != null && !firebaseUser.getPhoneNumber().isEmpty()) {
            db.collection(Constants.USER_COLLECTION_NAME)
                    .whereEqualTo("phoneNumber", getPhoneNumber(Objects.requireNonNull(firebaseUser.getPhoneNumber())))
                    .get()
                    .addOnSuccessListener(documentSnapshots -> {
                        if (documentSnapshots.isEmpty()) {
                            callback.onFailure(new Exception("User not found"));
                            return;
                        }
                        DocumentSnapshot document = documentSnapshots.getDocuments().get(0);
                        createUser(callback, document);
                    })
                    .addOnFailureListener(callback::onFailure);
        } else {
            db.collection(Constants.USER_COLLECTION_NAME)
                    .document(firebaseUser.getUid())
                    .get()
                    .addOnSuccessListener(document -> createUser(callback, document))
                    .addOnFailureListener(callback::onFailure);
        }
    }

    private static void createUser(IRepositoryCallback<User> callback, DocumentSnapshot document) {
        if(!document.exists()) {
            callback.onFailure(new Exception("User not found"));
            return;
        }
        User user = new User();
        user.setId(document.getId());
        user.setAvatarUrl(document.getString("avatarUrl"));
        Long birthdate = document.getLong("birthdate");
        String genderName = document.get("gender", String.class);
        User.Gender gender = User.Gender.valueOf(genderName);
        Long friendCount = document.getLong("friendCount");
        if (birthdate != null) {
            user.setBirthdate(birthdate);
        }
        user.setGender(gender);
        if (friendCount != null) {
            user.setFriendCount(friendCount);
        }
        user.setFullName(document.getString("fullName"));
        user.setPhoneNumber(document.getString("phoneNumber"));
        user.setBio(document.getString("bio"));
        user.setBackgroundUrl(document.getString("backgroundUrl"));
        user.setLastLogin(System.currentTimeMillis());
        callback.onSuccess(user);
    }

    private String getPhoneNumber(String phoneNumber) {
        return phoneNumber.replace("+84", "0");
    }

    @Override
    public void signOut() {
        auth.signOut();
    }
}
