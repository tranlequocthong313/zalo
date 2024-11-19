package vn.edu.ou.zalo.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.ICreateUseCase;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.GetSignedInUserUseCase;
import vn.edu.ou.zalo.domain.impl.SignInWithPhoneNumberAndPasswordUseCase;
import vn.edu.ou.zalo.domain.impl.SignOutUseCase;
import vn.edu.ou.zalo.ui.states.AuthUiState;

public class AuthViewModel extends ViewModel {
    private final MutableLiveData<AuthUiState> authUiStateMutableLiveData = new MutableLiveData<>(new AuthUiState(false, null, false, false, null));
    private final ICreateUseCase<User, Void> signUpUseCase;
    private final SignInWithPhoneNumberAndPasswordUseCase signInWithPhoneNumberAndPasswordUseCase;
    private final GetSignedInUserUseCase getSignedInUserUseCase;
    private final SignOutUseCase signOutUseCase;

    @Inject
    public AuthViewModel(ICreateUseCase<User, Void> signUpUseCase, SignInWithPhoneNumberAndPasswordUseCase signInWithPhoneNumberAndPasswordUseCase, GetSignedInUserUseCase getSignedInUserUseCase, SignOutUseCase signOutUseCase) {
        this.signUpUseCase = signUpUseCase;
        this.signInWithPhoneNumberAndPasswordUseCase = signInWithPhoneNumberAndPasswordUseCase;
        this.getSignedInUserUseCase = getSignedInUserUseCase;
        this.signOutUseCase = signOutUseCase;
    }

    public LiveData<AuthUiState> getUiState() {
        return authUiStateMutableLiveData;
    }

    public void signIn(String phoneNumber, String password) {
        authUiStateMutableLiveData.setValue(new AuthUiState(true, null, false, false, null));
        signInWithPhoneNumberAndPasswordUseCase.execute(phoneNumber, password, new IDomainCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                authUiStateMutableLiveData.setValue(new AuthUiState(false, null, false, result, null));
            }

            @Override
            public void onFailure(Exception e) {
                authUiStateMutableLiveData.setValue(new AuthUiState(false, e.getMessage(), false, false, null));
            }
        });
    }

    public void signUp(User user) {
        authUiStateMutableLiveData.setValue(new AuthUiState(true, null, false, false, null));
        signUpUseCase.execute(user, new IDomainCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                authUiStateMutableLiveData.setValue(new AuthUiState(false, null, true, false, null));
            }

            @Override
            public void onFailure(Exception e) {
                authUiStateMutableLiveData.setValue(new AuthUiState(false, e.getMessage(), false, false, null));
            }
        });
    }

    public void signOut() {
        signOutUseCase.execute();
    }

    public void checkIsSignedIn() {
        authUiStateMutableLiveData.setValue(new AuthUiState(true, null, false, false, null));
        getSignedInUserUseCase.execute(new IDomainCallback<User>() {
            @Override
            public void onSuccess(User user) {
                authUiStateMutableLiveData.setValue(new AuthUiState(false, null, false, user.getId() != null, null));
            }

            @Override
            public void onFailure(Exception e) {
                authUiStateMutableLiveData.setValue(new AuthUiState(false, e.getMessage(), false, false, null));
            }
        });
    }

    public void getSignedInUser() {
        getSignedInUserUseCase.execute(new IDomainCallback<User>() {
            @Override
            public void onSuccess(User user) {
                authUiStateMutableLiveData.setValue(new AuthUiState(false, null, false, user.getId() != null, user));
            }

            @Override
            public void onFailure(Exception e) {
                authUiStateMutableLiveData.setValue(new AuthUiState(false, e.getMessage(), false, false, null));
            }
        });
    }
}
