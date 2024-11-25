package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.DeleteFriendshipUseCase;
import vn.edu.ou.zalo.domain.impl.GetReceivedFriendRequests;
import vn.edu.ou.zalo.domain.impl.GetSentFriendRequests;
import vn.edu.ou.zalo.domain.impl.UpdateFriendshipStatusUseCase;
import vn.edu.ou.zalo.ui.states.FriendRequestUiState;

public class FriendRequestViewModel extends ViewModel {
    private final MutableLiveData<FriendRequestUiState> uiState = new MutableLiveData<>(new FriendRequestUiState(false, null, new ArrayList<>(), new ArrayList<>()));
    private final GetReceivedFriendRequests getReceivedFriendRequests;
    private final GetSentFriendRequests getSentFriendRequests;
    private final UpdateFriendshipStatusUseCase updateFriendshipStatusUseCase;
    private final DeleteFriendshipUseCase deleteFriendshipUseCase;

    @Inject
    public FriendRequestViewModel(GetReceivedFriendRequests getReceivedFriendRequests, GetSentFriendRequests getSentFriendRequests, UpdateFriendshipStatusUseCase updateFriendshipStatusUseCase, DeleteFriendshipUseCase deleteFriendshipUseCase) {
        this.getReceivedFriendRequests = getReceivedFriendRequests;
        this.getSentFriendRequests = getSentFriendRequests;
        this.updateFriendshipStatusUseCase = updateFriendshipStatusUseCase;
        this.deleteFriendshipUseCase = deleteFriendshipUseCase;
    }

    public LiveData<FriendRequestUiState> getUiState() {
        return uiState;
    }

    public void getReceivedFriendRequests() {
        uiState.setValue(new FriendRequestUiState(true, null, null, null));

        getReceivedFriendRequests.execute(new IDomainCallback<List<Friendship>>() {
            @Override
            public void onSuccess(List<Friendship> data) {
                uiState.setValue(new FriendRequestUiState(false, null, data, null));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendRequestUiState(false, null, null, null));
            }
        });
    }

    public void getSentFriendRequests() {
        uiState.setValue(new FriendRequestUiState(true, null, null, null));

        getSentFriendRequests.execute(new IDomainCallback<List<Friendship>>() {
            @Override
            public void onSuccess(List<Friendship> data) {
                uiState.setValue(new FriendRequestUiState(false, null, null, data));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendRequestUiState(false, null, null, null));
            }
        });
    }

    public void refuseFriendRequest(Friendship friendship) {
        deleteFriendshipUseCase.execute(friendship.getId(), new IDomainCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                List<Friendship> receivedFriendships = Objects.requireNonNull(uiState.getValue()).getReceivedRequests()
                        .stream()
                        .filter(f -> !Objects.equals(f.getId(), friendship.getId())).collect(Collectors.toList());
                uiState.setValue(new FriendRequestUiState(false, null, receivedFriendships, Objects.requireNonNull(uiState.getValue()).getSentRequests()));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendRequestUiState(false, e.getMessage(), Objects.requireNonNull(uiState.getValue()).getReceivedRequests(), Objects.requireNonNull(uiState.getValue()).getSentRequests()));
            }
        });
    }

    public void recallFriendRequest(Friendship friendship) {
        deleteFriendshipUseCase.execute(friendship.getId(), new IDomainCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                List<Friendship> sentFriendships = Objects.requireNonNull(uiState.getValue()).getSentRequests()
                        .stream()
                        .filter(f -> !Objects.equals(f.getId(), friendship.getId())).collect(Collectors.toList());
                uiState.setValue(new FriendRequestUiState(false, null, uiState.getValue().getReceivedRequests(), sentFriendships));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendRequestUiState(false, e.getMessage(), Objects.requireNonNull(uiState.getValue()).getReceivedRequests(), Objects.requireNonNull(uiState.getValue()).getSentRequests()));
            }
        });
    }

    public void acceptFriendRequest(Friendship friendship) {
        friendship.setStatus(Friendship.Status.ACCEPTED);
        updateFriendshipStatusUseCase.execute(friendship, new IDomainCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                List<Friendship> receivedFriendships = Objects.requireNonNull(uiState.getValue()).getReceivedRequests()
                        .stream()
                        .filter(f -> !Objects.equals(f.getId(), friendship.getId())).collect(Collectors.toList());
                uiState.setValue(new FriendRequestUiState(false, null, receivedFriendships, Objects.requireNonNull(uiState.getValue()).getSentRequests()));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendRequestUiState(false, e.getMessage(), Objects.requireNonNull(uiState.getValue()).getReceivedRequests(), Objects.requireNonNull(uiState.getValue()).getSentRequests()));
            }
        });
    }
}
