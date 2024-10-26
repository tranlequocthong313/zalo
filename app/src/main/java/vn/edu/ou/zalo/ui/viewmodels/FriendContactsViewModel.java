package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.di.qualifiers.AddedFriends;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.FriendContactsUiState;

public class FriendContactsViewModel extends ViewModel {
    private final MutableLiveData<FriendContactsUiState> uiState =
            new MutableLiveData<>(new FriendContactsUiState(false, null, null));
    private final IGetListUseCase<User> getFriendsUseCase;

    @Inject
    public FriendContactsViewModel(@AddedFriends IGetListUseCase<User> getFriendsUseCase) {
        this.getFriendsUseCase = getFriendsUseCase;

        fetchData();
    }

    public LiveData<FriendContactsUiState> getUiState() {
        return uiState;
    }

    private void fetchData() {
        uiState.setValue(new FriendContactsUiState(true, null, null));

        try {
            List<User> friends = getFriendsUseCase.execute();
            uiState.setValue(new FriendContactsUiState(false, null, friends));
        } catch (Exception e) {
            uiState.setValue(new FriendContactsUiState(false, e.getMessage(), null));
        }
    }
}
