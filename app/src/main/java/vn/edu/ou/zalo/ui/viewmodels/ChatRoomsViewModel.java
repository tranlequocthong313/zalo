package vn.edu.ou.zalo.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.ChatRoomUiState;

public class ChatRoomsViewModel extends ViewModel {
    private static final String TAG = "ChatRoomsViewModel";

    private final MutableLiveData<ChatRoomUiState> uiState =
            new MutableLiveData<>(new ChatRoomUiState(null, false, null));

    private final IGetListUseCase<ChatRoom> getChatRoomsUseCase;

    @Inject
    public ChatRoomsViewModel(IGetListUseCase<ChatRoom> getChatRoomsUseCase) {
        this.getChatRoomsUseCase = getChatRoomsUseCase;
        getChatRooms();
    }

    public LiveData<ChatRoomUiState> getUiState() {
        return uiState;
    }

    private void getChatRooms() {
        uiState.setValue(new ChatRoomUiState(null, true, null));

        try {
            List<ChatRoom> chatRooms = getChatRoomsUseCase.execute();
            Log.i(TAG, "Fetch Chat Rooms: " + chatRooms.size());
            uiState.setValue(new ChatRoomUiState(chatRooms, false, null));
        } catch (Exception e) {
            uiState.setValue(new ChatRoomUiState(null, false, e.getMessage()));
        }
    }
}
