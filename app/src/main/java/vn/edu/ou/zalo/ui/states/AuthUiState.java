package vn.edu.ou.zalo.ui.states;

import vn.edu.ou.zalo.data.models.User;

public class AuthUiState extends BaseUiState {
    private final boolean isSignedUp;
    private final boolean isSignedIn;
    private final User currentSignedInUser;

    public AuthUiState(boolean isLoading, String errorMessage, boolean isSignedUp, boolean isSignedIn, User currentSignedInUser) {
        super(isLoading, errorMessage);
        this.isSignedUp = isSignedUp;
        this.isSignedIn = isSignedIn;
        this.currentSignedInUser = currentSignedInUser;
    }

    public boolean isSignedUp() {
        return isSignedUp;
    }

    public User getCurrentSignedInUser() {
        return currentSignedInUser;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }
}
