package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.OtherChatRoomUiState;

public class OtherChatRoomsViewModel extends ViewModel {

    private static final int MAX_CHAT_ROOM_SUGGESTIONS = 3;

    private final MutableLiveData<OtherChatRoomUiState> uiState =
            new MutableLiveData<>(new OtherChatRoomUiState(false, null, null, null));
    private final IGetListUseCase<ChatRoom> getChatRoomsUseCase;
    private List<ChatRoom> focusedChatRooms;

    @Inject
    public OtherChatRoomsViewModel(IGetListUseCase<ChatRoom> getChatRoomsUseCase) {
        this.getChatRoomsUseCase = getChatRoomsUseCase;

        fetchData();
    }

    public LiveData<OtherChatRoomUiState> getUiState() {
        return uiState;
    }

    private void fetchData() {
        uiState.setValue(new OtherChatRoomUiState(true, null, null, null));

        try {
            getChatRoomsUseCase.execute(Map.of("priority", ChatRoom.Priority.OTHER.name()), new IDomainCallback<List<ChatRoom>>() {
                @Override
                public void onSuccess(List<ChatRoom> data) {
                    uiState.setValue(new OtherChatRoomUiState(false, null, data, getLimitedChatRoomSuggestions()));
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
            getChatRoomsUseCase.execute(Map.of("priority", ChatRoom.Priority.FOCUSED.name()), new IDomainCallback<List<ChatRoom>>() {
                @Override
                public void onSuccess(List<ChatRoom> data) {
                    focusedChatRooms = data;
                    uiState.setValue(new OtherChatRoomUiState(false, null, Objects.requireNonNull(uiState.getValue()).getChatRooms(), getLimitedChatRoomSuggestions()));
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        } catch (Exception e) {
            uiState.setValue(new OtherChatRoomUiState(false, e.getMessage(), null, null));
        }
    }

    private List<ChatRoom> getLimitedChatRoomSuggestions() {
        return focusedChatRooms != null ?
                focusedChatRooms.subList(0, Math.min(MAX_CHAT_ROOM_SUGGESTIONS, focusedChatRooms.size())) :
                new ArrayList<>();
    }

    public void showAllFriendSuggestions() {
        uiState.setValue(
                new OtherChatRoomUiState(
                        false,
                        null,
                        Objects.requireNonNull(uiState.getValue()).getChatRooms(),
                        focusedChatRooms
                )
        );
    }
}
