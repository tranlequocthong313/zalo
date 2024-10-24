package vn.edu.ou.zalo.ui.states;

public abstract class BaseUiState {
    private final boolean isLoading;
    private final String errorMessage;

    public BaseUiState(boolean isLoading, String errorMessage) {
        this.isLoading = isLoading;
        this.errorMessage = errorMessage;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
