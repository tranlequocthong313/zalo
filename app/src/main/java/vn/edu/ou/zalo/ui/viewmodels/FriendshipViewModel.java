package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.ICreateUseCase;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.GetFriendRecommendations;
import vn.edu.ou.zalo.ui.states.FriendshipUiState;

public class FriendshipViewModel extends ViewModel {
    private final MutableLiveData<FriendshipUiState> uiState =
            new MutableLiveData<>(new FriendshipUiState(false, null, new ArrayList<>()));

    private final ICreateUseCase<User, Friendship> addFriendUseCase;
    private final GetFriendRecommendations getFriendRecommendationsUseCase;

    public LiveData<FriendshipUiState> getUiState() {
        return uiState;
    }

    @Inject
    public FriendshipViewModel(ICreateUseCase<User, Friendship> addFriendUseCase, GetFriendRecommendations getFriendRecommendationsUseCase) {
        this.addFriendUseCase = addFriendUseCase;
        this.getFriendRecommendationsUseCase = getFriendRecommendationsUseCase;
    }

    public void fetchFriendRecommendations() {
        uiState.setValue(new FriendshipUiState(true, null, new ArrayList<>()));
        getFriendRecommendationsUseCase.execute(new IDomainCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                uiState.setValue(new FriendshipUiState(false, null, data));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendshipUiState(false, e.getMessage(), new ArrayList<>()));
            }
        });
    }

    public void addFriend(User friend) {
        addFriendUseCase.execute(friend, new IDomainCallback<Friendship>() {
            @Override
            public void onSuccess(Friendship data) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
