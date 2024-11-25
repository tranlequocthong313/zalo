package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.GetAddedFriendsUseCase;
import vn.edu.ou.zalo.ui.states.FriendContactsUiState;

public class FriendContactsViewModel extends ViewModel {
    private final MutableLiveData<FriendContactsUiState> uiState =
            new MutableLiveData<>(new FriendContactsUiState(false, null, new ArrayList<>()));
    private final GetAddedFriendsUseCase getFriendsUseCase;

    @Inject
    public FriendContactsViewModel(GetAddedFriendsUseCase getFriendsUseCase) {
        this.getFriendsUseCase = getFriendsUseCase;
    }

    public LiveData<FriendContactsUiState> getUiState() {
        return uiState;
    }

    public void fetchFriends() {
        uiState.setValue(new FriendContactsUiState(true, null, new ArrayList<>()));

        getFriendsUseCase.execute(new IDomainCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                uiState.setValue(new FriendContactsUiState(false, null, data));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FriendContactsUiState(false, e.getMessage(), null));
            }
        });
    }
}
