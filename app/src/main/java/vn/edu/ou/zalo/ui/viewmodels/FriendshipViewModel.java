package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.CheckFriendStatusUseCase;
import vn.edu.ou.zalo.domain.impl.GetFriendRecommendations;
import vn.edu.ou.zalo.domain.impl.SendFriendRequestUseCase;
import vn.edu.ou.zalo.ui.states.FriendshipUiState;

public class FriendshipViewModel extends ViewModel {
    private final MutableLiveData<FriendshipUiState> uiState =
            new MutableLiveData<>(new FriendshipUiState(false, null, new ArrayList<>(), null, null));

    private final SendFriendRequestUseCase sendFriendRequestUseCase;
    private final GetFriendRecommendations getFriendRecommendationsUseCase;
    private final CheckFriendStatusUseCase checkFriendStatusUseCase;

    private Map<String, Friendship.Status> friendshipStatuses = new HashMap<>();

    public LiveData<FriendshipUiState> getUiState() {
        return uiState;
    }

    @Inject
    public FriendshipViewModel(SendFriendRequestUseCase sendFriendRequestUseCase, GetFriendRecommendations getFriendRecommendationsUseCase, CheckFriendStatusUseCase checkFriendStatusUseCase) {
        this.sendFriendRequestUseCase = sendFriendRequestUseCase;
        this.getFriendRecommendationsUseCase = getFriendRecommendationsUseCase;
        this.checkFriendStatusUseCase = checkFriendStatusUseCase;
    }

    public void fetchFriendRecommendations() {
        uiState.setValue(new FriendshipUiState(true, null, new ArrayList<>(), null, Objects.requireNonNull(uiState.getValue()).getFriendshipStatuses()));
        getFriendRecommendationsUseCase.execute(new IDomainCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                uiState.setValue(new FriendshipUiState(false, null, data, null, Objects.requireNonNull(uiState.getValue()).getFriendshipStatuses()));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendshipUiState(false, e.getMessage(), new ArrayList<>(), null, Objects.requireNonNull(uiState.getValue()).getFriendshipStatuses()));
            }
        });
    }

    public void sendFriendRequest(User friend) {
        sendFriendRequestUseCase.execute(friend, new IDomainCallback<Friendship>() {
            @Override
            public void onSuccess(Friendship data) {
                uiState.setValue(new FriendshipUiState(false, null, Objects.requireNonNull(uiState.getValue()).getFriendRecommendations(), Friendship.Status.PENDING, Objects.requireNonNull(uiState.getValue()).getFriendshipStatuses()));
                List<User> users = new ArrayList<>();
                users.add(friend);
                checkFriendStatuses(users);
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendshipUiState(false, null, Objects.requireNonNull(uiState.getValue()).getFriendRecommendations(), null, Objects.requireNonNull(uiState.getValue()).getFriendshipStatuses()));
            }
        });
    }

    public void checkFriendStatus(User friend) {
        checkFriendStatusUseCase.execute(friend, new IDomainCallback<Friendship.Status>() {
            @Override
            public void onSuccess(Friendship.Status data) {
                uiState.setValue(new FriendshipUiState(false, null, Objects.requireNonNull(uiState.getValue()).getFriendRecommendations(), data, Objects.requireNonNull(uiState.getValue()).getFriendshipStatuses()));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendshipUiState(false, e.getMessage(), Objects.requireNonNull(uiState.getValue()).getFriendRecommendations(), null, Objects.requireNonNull(uiState.getValue()).getFriendshipStatuses()));
            }
        });
    }


    public void checkFriendStatuses(List<User> friends) {
        for (User friend : friends) {
            checkFriendStatusUseCase.execute(friend, new IDomainCallback<Friendship.Status>() {
                @Override
                public void onSuccess(Friendship.Status data) {
                    Map<String, Friendship.Status> newFriendshipStatuses = new HashMap<>(friendshipStatuses);
                    newFriendshipStatuses.put(friend.getId(), data);
                    friendshipStatuses = newFriendshipStatuses;
                    List<User> friendRecommendations = Objects.requireNonNull(uiState.getValue())
                            .getFriendRecommendations()
                            .stream()
                            .filter(friend -> !newFriendshipStatuses.containsKey(friend.getId())).collect(Collectors.toList());
                    uiState.setValue(new FriendshipUiState(false, null, friendRecommendations, data, newFriendshipStatuses));
                }

                @Override
                public void onFailure(Exception e) {
                    uiState.setValue(new FriendshipUiState(false, e.getMessage(), Objects.requireNonNull(uiState.getValue()).getFriendRecommendations(), null, Objects.requireNonNull(uiState.getValue()).getFriendshipStatuses()));
                }
            });
        }
    }
}
