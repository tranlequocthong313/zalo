package vn.edu.ou.zalo.ui.states;

public class OtpUiState extends BaseUiState {
    private final String verificationId;
    private final boolean isValidOtp;

    public OtpUiState(String verificationId, boolean isValidOtp, boolean isLoading, String errorMessage) {
        super(isLoading, errorMessage);

        this.verificationId = verificationId;
        this.isValidOtp = isValidOtp;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public boolean isVerifiedOtp() {
        return isValidOtp;
    }
}
