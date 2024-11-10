package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import javax.inject.Inject;

import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.SendOtpUseCase;
import vn.edu.ou.zalo.domain.impl.VerifyOtpUseCase;
import vn.edu.ou.zalo.ui.states.OtpUiState;

public class OtpViewModel extends ViewModel {
    private final MutableLiveData<OtpUiState> uiState = new MutableLiveData<>(
            new OtpUiState(null, false, false, null));
    private final SendOtpUseCase sendOtpUseCase;
    private final VerifyOtpUseCase verifyOtpUseCase;

    public LiveData<OtpUiState> getUiState() {
        return uiState;
    }

    @Inject
    public OtpViewModel(SendOtpUseCase sendOtpUseCase, VerifyOtpUseCase verifyOtpUseCase) {
        this.sendOtpUseCase = sendOtpUseCase;
        this.verifyOtpUseCase = verifyOtpUseCase;
    }

    public void sendOtp(String phoneNumber) {
        sendOtpUseCase.execute(phoneNumber, new IDomainCallback<String>() {
            @Override
            public void onSuccess(String data) {
                uiState.setValue(new OtpUiState(data, false, false, null));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new OtpUiState(null, false, false, e.getMessage()));
            }
        });
    }

    public void verifyOtp(String otp) {
        verifyOtpUseCase.execute(
                Objects.requireNonNull(uiState.getValue()).getVerificationId(),
                otp,
                new IDomainCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        uiState.setValue(new OtpUiState(uiState.getValue().getVerificationId(), data, false, null));
                    }

                    @Override
                    public void onFailure(Exception e) {
                        uiState.setValue(new OtpUiState(uiState.getValue().getVerificationId(), false, false, e.getMessage()));
                    }
                }
        );
    }
}
