package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;

public class FriendRequestUiState extends BaseUiState {
    private final List<Friendship> receivedRequests;
    private final List<Friendship> sentRequests;

    public FriendRequestUiState(boolean isLoading, String errorMessage, List<Friendship> receivedRequests, List<Friendship> sentRequests) {
        super(isLoading, errorMessage);
        this.receivedRequests = receivedRequests;
        this.sentRequests = sentRequests;
    }

    public List<Friendship> getReceivedRequests() {
        return receivedRequests;
    }

    public List<Friendship> getSentRequests() {
        return sentRequests;
    }
}
