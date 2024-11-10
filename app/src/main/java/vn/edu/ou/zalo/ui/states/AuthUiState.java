package vn.edu.ou.zalo.ui.states;

public class AuthUiState extends BaseUiState {
    private final boolean isSignedUp;
    private final boolean isSignedIn;

    public AuthUiState(boolean isLoading, String errorMessage, boolean isSignedUp, boolean isSignedIn) {
        super(isLoading, errorMessage);
        this.isSignedUp = isSignedUp;
        this.isSignedIn = isSignedIn;
    }

    public boolean isSignedUp() {
        return isSignedUp;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }
}
