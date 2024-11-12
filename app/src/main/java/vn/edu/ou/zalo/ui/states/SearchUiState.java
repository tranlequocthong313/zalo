package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.User;

public class SearchUiState extends BaseUiState {
    private final List<User> users;

    public SearchUiState(boolean isLoading, String errorMessage, List<User> users) {
        super(isLoading, errorMessage);
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
