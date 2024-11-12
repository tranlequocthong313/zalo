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
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.CheckEmptyChatRoomUseCase;
import vn.edu.ou.zalo.ui.states.ChatRoomUiState;

public class ChatRoomsViewModel extends ViewModel {
    private final MutableLiveData<ChatRoomUiState> uiState =
            new MutableLiveData<>(new ChatRoomUiState(false, null, new ArrayList<>(), false, false));
    private final IGetListUseCase<ChatRoom> getChatRoomsUseCase;
    private final CheckEmptyChatRoomUseCase checkEmptyChatRoomUseCase;

    @Inject
    public ChatRoomsViewModel(IGetListUseCase<ChatRoom> getChatRoomsUseCase, CheckEmptyChatRoomUseCase checkEmptyChatRoomUseCase) {
        this.getChatRoomsUseCase = getChatRoomsUseCase;
        this.checkEmptyChatRoomUseCase = checkEmptyChatRoomUseCase;
    }

    public LiveData<ChatRoomUiState> getUiState() {
        return uiState;
    }

    public void fetchData(ChatRoom.Priority priority) {
        uiState.setValue(new ChatRoomUiState(true, null, new ArrayList<>(), false, false));

        getChatRoomsUseCase.execute(Map.of("priority", priority.name()), new IDomainCallback<List<ChatRoom>>() {
            @Override
            public void onSuccess(List<ChatRoom> chatRooms) {
                uiState.setValue(new ChatRoomUiState(false, null, chatRooms, false, false));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatRoomUiState(false, e.getMessage(), new ArrayList<>(), false, false));
            }
        });
    }

    public void checkEmptyChatRoom() {
        checkEmptyChatRoomUseCase.execute(new IDomainCallback<Map<ChatRoom.Priority, Boolean>>() {
            @Override
            public void onSuccess(Map<ChatRoom.Priority, Boolean> isEmptyMap) {
                boolean isFocusedEmpty = Boolean.TRUE.equals(isEmptyMap.getOrDefault(ChatRoom.Priority.FOCUSED, true));
                boolean isOtherEmpty = Boolean.TRUE.equals(isEmptyMap.getOrDefault(ChatRoom.Priority.OTHER, true));

                if (isFocusedEmpty && isOtherEmpty) {
                    uiState.setValue(new ChatRoomUiState(false, null, new ArrayList<>(), true, true));
                } else if (isFocusedEmpty) {
                    uiState.setValue(new ChatRoomUiState(false, null, new ArrayList<>(), true, false));
                } else if (isOtherEmpty) {
                    uiState.setValue(new ChatRoomUiState(false, null, new ArrayList<>(), false, true));
                } else {
                    uiState.setValue(new ChatRoomUiState(false, null, new ArrayList<>(), false, false));
                }
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatRoomUiState(false, e.getMessage(), new ArrayList<>(), false, false));
            }
        });
    }
}
