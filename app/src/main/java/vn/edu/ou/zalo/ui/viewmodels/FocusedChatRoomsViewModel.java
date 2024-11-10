package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.FocusedChatRoomUiState;

public class FocusedChatRoomsViewModel extends ViewModel {

    private static final int MAX_FRIEND_SUGGESTIONS = 3;

    private final MutableLiveData<FocusedChatRoomUiState> uiState =
            new MutableLiveData<>(new FocusedChatRoomUiState(false, null, null, null));
    private final IGetListUseCase<ChatRoom> getChatRoomsUseCase;
    private final IGetListUseCase<User> getFriendSuggestionsUseCase;
    private List<User> friendSuggestions = new ArrayList<>();
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Inject
    public FocusedChatRoomsViewModel(IGetListUseCase<ChatRoom> getChatRoomsUseCase, IGetListUseCase<User> getFriendSuggestionsUseCase) {
        this.getChatRoomsUseCase = getChatRoomsUseCase;
        this.getFriendSuggestionsUseCase = getFriendSuggestionsUseCase;

        fetchData();
    }

    public LiveData<FocusedChatRoomUiState> getUiState() {
        return uiState;
    }

    private void fetchData() {
        uiState.setValue(new FocusedChatRoomUiState(true, null, null, null));

        getChatRoomsUseCase.execute(Map.of("priority", ChatRoom.Priority.FOCUSED.name()), new IDomainCallback<List<ChatRoom>>() {
            @Override
            public void onSuccess(List<ChatRoom> chatRooms) {
                FocusedChatRoomsViewModel.this.chatRooms = chatRooms;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FocusedChatRoomUiState(false, e.getMessage(), null, null));
            }
        });

        getFriendSuggestionsUseCase.execute(null, new IDomainCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                friendSuggestions = users;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new FocusedChatRoomUiState(false, e.getMessage(), null, null));
            }
        });
    }

    private void updateUiState() {
        uiState.setValue(new FocusedChatRoomUiState(false, null, chatRooms, getLimitedFriendSuggestions(friendSuggestions)));
    }

    private List<ChatRoom> getChatRooms() {
        FocusedChatRoomUiState currentState = uiState.getValue();
        return currentState != null ? new ArrayList<>(currentState.getChatRooms()) : Collections.emptyList();
    }

    private List<User> getLimitedFriendSuggestions(List<User> friendSuggestions) {
        return friendSuggestions != null
                ? new ArrayList<>(friendSuggestions.subList(0, Math.min(MAX_FRIEND_SUGGESTIONS, friendSuggestions.size())))
                : new ArrayList<>();
    }

    public void showAllFriendSuggestions() {
        uiState.setValue(
                new FocusedChatRoomUiState(
                        false,
                        null,
                        getChatRooms(),
                        friendSuggestions
                )
        );
    }
}
