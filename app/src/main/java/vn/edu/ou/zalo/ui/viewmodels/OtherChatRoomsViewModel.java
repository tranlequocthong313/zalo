package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.di.qualifiers.ImportantChatRooms;
import vn.edu.ou.zalo.di.qualifiers.UnimportantChatRooms;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.OtherChatRoomUiState;

public class OtherChatRoomsViewModel extends ViewModel {

    private static final int MAX_CHAT_ROOM_SUGGESTIONS = 3;

    private final MutableLiveData<OtherChatRoomUiState> uiState =
            new MutableLiveData<>(new OtherChatRoomUiState(false, null, null, null));
    private final IGetListUseCase<ChatRoom> getChatRoomsUseCase;
    private final IGetListUseCase<ChatRoom> getUnimportantChatRooms;
    private List<ChatRoom> importantChatRooms;

    @Inject
    public OtherChatRoomsViewModel(@ImportantChatRooms IGetListUseCase<ChatRoom> getChatRoomsUseCase, @UnimportantChatRooms IGetListUseCase<ChatRoom> getUnimportantChatRooms) {
        this.getChatRoomsUseCase = getChatRoomsUseCase;
        this.getUnimportantChatRooms = getUnimportantChatRooms;

        fetchData();
    }

    public LiveData<OtherChatRoomUiState> getUiState() {
        return uiState;
    }

    private void fetchData() {
        uiState.setValue(new OtherChatRoomUiState(true, null, null, null));

        try {
            List<ChatRoom> chatRooms = getUnimportantChatRooms.execute();
            importantChatRooms = getChatRoomsUseCase.execute();
            uiState.setValue(new OtherChatRoomUiState(false, null, chatRooms, getLimitedChatRoomSuggestions()));
        } catch (Exception e) {
            uiState.setValue(new OtherChatRoomUiState(false, e.getMessage(), null, null));
        }
    }

    private List<ChatRoom> getLimitedChatRoomSuggestions() {
        return importantChatRooms != null ?
                importantChatRooms.subList(0, Math.min(MAX_CHAT_ROOM_SUGGESTIONS, importantChatRooms.size())) :
                new ArrayList<>();
    }

    public void showAllFriendSuggestions() {
        uiState.setValue(
                new OtherChatRoomUiState(
                        false,
                        null,
                        Objects.requireNonNull(uiState.getValue()).getChatRooms(),
                        importantChatRooms
                )
        );
    }
}
