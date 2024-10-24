package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.di.ImportantChatRooms;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.FocusedChatRoomUiState;

public class FocusedChatRoomsViewModel extends ViewModel {

    private static final int MAX_FRIEND_SUGGESTIONS = 3;

    private final MutableLiveData<FocusedChatRoomUiState> uiState =
            new MutableLiveData<>(new FocusedChatRoomUiState(false, null, null, null));
    private final IGetListUseCase<ChatRoom> getChatRoomsUseCase;
    private final IGetListUseCase<User> getFriendSuggestionsUseCase;
    private List<User> friendSuggestions;

    @Inject
    public FocusedChatRoomsViewModel(@ImportantChatRooms IGetListUseCase<ChatRoom> getChatRoomsUseCase, IGetListUseCase<User> getFriendSuggestionsUseCase) {
        this.getChatRoomsUseCase = getChatRoomsUseCase;
        this.getFriendSuggestionsUseCase = getFriendSuggestionsUseCase;

        fetchData();
    }

    public LiveData<FocusedChatRoomUiState> getUiState() {
        return uiState;
    }

    private void fetchData() {
        uiState.setValue(new FocusedChatRoomUiState(true, null, null, null));

        try {
            List<ChatRoom> chatRooms = getChatRoomsUseCase.execute();
            friendSuggestions = getFriendSuggestionsUseCase.execute();
            uiState.setValue(new FocusedChatRoomUiState(false, null, chatRooms, getLimitedFriendSuggestions()));
        } catch (Exception e) {
            uiState.setValue(new FocusedChatRoomUiState(false, e.getMessage(), null, null));
        }
    }

    private List<User> getLimitedFriendSuggestions() {
        return friendSuggestions != null ?
                friendSuggestions.subList(0, Math.min(MAX_FRIEND_SUGGESTIONS, friendSuggestions.size())) :
                new ArrayList<>();
    }

    public void showAllFriendSuggestions() {
        uiState.setValue(
                new FocusedChatRoomUiState(
                        false,
                        null,
                        Objects.requireNonNull(uiState.getValue()).getChatRooms(),
                        friendSuggestions
                )
        );
    }
}
