package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.GroupChatRoomUiState;

public class GroupChatRoomsViewModel extends ViewModel {
    private final MutableLiveData<GroupChatRoomUiState> uiState =
            new MutableLiveData<>(new GroupChatRoomUiState(false, null, null));
    private final IGetListUseCase<ChatRoom> getGroupChatRoomsUseCase;

    @Inject
    public GroupChatRoomsViewModel(IGetListUseCase<ChatRoom> getGroupChatRoomsUseCase) {
        this.getGroupChatRoomsUseCase = getGroupChatRoomsUseCase;

        fetchData();
    }

    public LiveData<GroupChatRoomUiState> getUiState() {
        return uiState;
    }

    private void fetchData() {
        uiState.setValue(new GroupChatRoomUiState(true, null, null));

        try {
            List<ChatRoom> chatRooms = getGroupChatRoomsUseCase.execute();
            uiState.setValue(new GroupChatRoomUiState(false, null, chatRooms));
        } catch (Exception e) {
            uiState.setValue(new GroupChatRoomUiState(false, e.getMessage(), null));
        }
    }
}
