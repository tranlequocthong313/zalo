package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.GetChatRoomsUseCase;
import vn.edu.ou.zalo.domain.impl.GetSignedInUserUseCase;
import vn.edu.ou.zalo.ui.states.ChatRoomUiState;
import vn.edu.ou.zalo.ui.states.GroupChatRoomUiState;

public class GroupChatRoomsViewModel extends ViewModel {
    private final MutableLiveData<GroupChatRoomUiState> uiState =
            new MutableLiveData<>(new GroupChatRoomUiState(false, null, null, null));
    private final GetChatRoomsUseCase getGroupChatRoomsUseCase;
    private final GetSignedInUserUseCase getSignedInUserUseCase;

    @Inject
    public GroupChatRoomsViewModel(GetChatRoomsUseCase getGroupChatRoomsUseCase, GetSignedInUserUseCase getSignedInUserUseCase) {
        this.getGroupChatRoomsUseCase = getGroupChatRoomsUseCase;
        this.getSignedInUserUseCase = getSignedInUserUseCase;

        getSignedInUser();

        fetchData();
    }

    public void getSignedInUser() {
        getSignedInUserUseCase.execute(new IDomainCallback<User>() {
            @Override
            public void onSuccess(User user) {
                uiState.setValue(new GroupChatRoomUiState(false, null, Objects.requireNonNull(uiState.getValue()).getChatRooms(), user));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new GroupChatRoomUiState(false, e.getMessage(), Objects.requireNonNull(uiState.getValue()).getChatRooms(), null));
            }
        });
    }

    public LiveData<GroupChatRoomUiState> getUiState() {
        return uiState;
    }

    private void fetchData() {
        uiState.setValue(new GroupChatRoomUiState(true, null, null, null));

        try {
            getGroupChatRoomsUseCase.execute(Map.of("type", ChatRoom.Type.GROUP.name()), new IDomainCallback<List<ChatRoom>>() {
                @Override
                public void onSuccess(List<ChatRoom> data) {
                    uiState.setValue(new GroupChatRoomUiState(false, null, data, null));
                }

                @Override
                public void onFailure(Exception e) {
                    uiState.setValue(new GroupChatRoomUiState(false, e.getMessage(), null, null));
                }
            });
        } catch (Exception e) {
            uiState.setValue(new GroupChatRoomUiState(false, e.getMessage(), null, null));
        }
    }
}
