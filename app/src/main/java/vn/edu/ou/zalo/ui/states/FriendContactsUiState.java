package vn.edu.ou.zalo.ui.states;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vn.edu.ou.zalo.data.models.User;

public class FriendContactsUiState extends BaseUiState {
    private final List<User> friends;

    public FriendContactsUiState(boolean isLoading, String errorMessage, List<User> friends) {
        super(isLoading, errorMessage);
        this.friends = friends;
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<User> getOnlineFriends() {
        if (friends == null) {
            return new ArrayList<>();
        }
        return friends.stream()
                .filter(User::isOnline)
                .collect(Collectors.toList());
    }
}
