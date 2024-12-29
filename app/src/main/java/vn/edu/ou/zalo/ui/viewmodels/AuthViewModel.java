package vn.edu.ou.zalo.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.AuthenticateCallServiceUseCase;
import vn.edu.ou.zalo.domain.impl.GetSignedInUserUseCase;
import vn.edu.ou.zalo.domain.impl.SignInWithPhoneNumberAndPasswordUseCase;
import vn.edu.ou.zalo.domain.impl.SignOutUseCase;
import vn.edu.ou.zalo.domain.impl.SignUpUseCase;
import vn.edu.ou.zalo.ui.states.AuthUiState;

public class AuthViewModel extends ViewModel {
    private final MutableLiveData<AuthUiState> uiState = new MutableLiveData<>(new AuthUiState(false, null, false, false, null));
    private final SignUpUseCase signUpUseCase;
    private final SignInWithPhoneNumberAndPasswordUseCase signInWithPhoneNumberAndPasswordUseCase;
    private final GetSignedInUserUseCase getSignedInUserUseCase;
    private final SignOutUseCase signOutUseCase;
    private final AuthenticateCallServiceUseCase authenticateCallServiceUseCase;

    @Inject
    public AuthViewModel(SignUpUseCase signUpUseCase, SignInWithPhoneNumberAndPasswordUseCase signInWithPhoneNumberAndPasswordUseCase, GetSignedInUserUseCase getSignedInUserUseCase, SignOutUseCase signOutUseCase, AuthenticateCallServiceUseCase authenticateCallServiceUseCase) {
        this.signUpUseCase = signUpUseCase;
        this.signInWithPhoneNumberAndPasswordUseCase = signInWithPhoneNumberAndPasswordUseCase;
        this.getSignedInUserUseCase = getSignedInUserUseCase;
        this.signOutUseCase = signOutUseCase;
        this.authenticateCallServiceUseCase = authenticateCallServiceUseCase;
    }

    public LiveData<AuthUiState> getUiState() {
        return uiState;
    }

    private void authenticateCallService() {
        authenticateCallServiceUseCase.execute(new IDomainCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                Log.i("authenticate", "authenticated successfully");
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("authenticate", Objects.requireNonNull(e.getMessage()));
            }
        });
    }

    public void signIn(String phoneNumber, String password) {
        uiState.setValue(new AuthUiState(true, null, false, false, null));
        signInWithPhoneNumberAndPasswordUseCase.execute(phoneNumber, password, new IDomainCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                uiState.setValue(new AuthUiState(false, null, false, result, null));
                authenticateCallService();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new AuthUiState(false, e.getMessage(), false, false, null));
            }
        });
    }

    public void signUp(User user) {
        uiState.setValue(new AuthUiState(true, null, false, false, null));
        signUpUseCase.execute(user, new IDomainCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                uiState.setValue(new AuthUiState(false, null, true, false, null));
                authenticateCallService();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new AuthUiState(false, e.getMessage(), false, false, null));
            }
        });
    }

    public void signOut() {
        signOutUseCase.execute();
    }

    public void checkIsSignedIn() {
        uiState.setValue(new AuthUiState(true, null, false, false, Objects.requireNonNull(uiState.getValue()).getCurrentSignedInUser()));
        getSignedInUserUseCase.execute(new IDomainCallback<User>() {
            @Override
            public void onSuccess(User user) {
                uiState.setValue(new AuthUiState(false, null, false, user.getId() != null, Objects.requireNonNull(uiState.getValue()).getCurrentSignedInUser()));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new AuthUiState(false, e.getMessage(), false, false, Objects.requireNonNull(uiState.getValue()).getCurrentSignedInUser()));
            }
        });
    }

    public void getSignedInUser() {
        getSignedInUserUseCase.execute(new IDomainCallback<User>() {
            @Override
            public void onSuccess(User user) {
                uiState.setValue(new AuthUiState(false, null, false, user.getId() != null, user));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new AuthUiState(false, e.getMessage(), false, false, Objects.requireNonNull(uiState.getValue()).getCurrentSignedInUser()));
            }
        });
    }
}
