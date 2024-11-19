package vn.edu.ou.zalo.ui.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;

public class FriendshipUiState extends BaseUiState {
    private static final int MAX_FRIEND_RECOMMENDATIONS = 3;

    private final List<User> friendRecommendations;
    private final Friendship.Status status;
    private final Map<String, Friendship.Status> friendshipStatuses;

    public FriendshipUiState(boolean isLoading, String errorMessage, List<User> friendRecommendations, Friendship.Status status, Map<String, Friendship.Status> friendshipStatuses) {
        super(isLoading, errorMessage);
        this.friendRecommendations = friendRecommendations;
        this.status = status;
        this.friendshipStatuses = friendshipStatuses;
    }

    public Friendship.Status getStatus() {
        return status;
    }

    public Map<String, Friendship.Status> getFriendshipStatuses() {
        return friendshipStatuses;
    }

    public List<User> getFriendRecommendations() {
        return friendRecommendations;
    }

    public List<User> getLimitedFriendRecommendations() {
        return getLimitedFriendRecommendations(MAX_FRIEND_RECOMMENDATIONS);
    }

    public List<User> getLimitedFriendRecommendations(int limit) {
        return friendRecommendations != null
                ? new ArrayList<>(friendRecommendations.subList(0, Math.min(limit, friendRecommendations.size())))
                : new ArrayList<>();
    }
}
