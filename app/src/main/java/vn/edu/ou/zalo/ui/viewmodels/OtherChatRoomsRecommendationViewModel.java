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

public class OtherChatRoomsRecommendationViewModel extends ViewModel {

    private final MutableLiveData<OtherChatRoomUiState> uiState =
            new MutableLiveData<>(new OtherChatRoomUiState(false, null, new ArrayList<>()));
    private final IGetListUseCase<ChatRoom> getChatRoomsUseCase;

    @Inject
    public OtherChatRoomsRecommendationViewModel(IGetListUseCase<ChatRoom> getChatRoomsUseCase) {
        this.getChatRoomsUseCase = getChatRoomsUseCase;

        fetchData();
    }

    public LiveData<OtherChatRoomUiState> getUiState() {
        return uiState;
    }

    private void fetchData() {
        uiState.setValue(new OtherChatRoomUiState(true, null, new ArrayList<>()));

        try {
            getChatRoomsUseCase.execute(Map.of("priority", ChatRoom.Priority.FOCUSED.name()), new IDomainCallback<List<ChatRoom>>() {
                @Override
                public void onSuccess(List<ChatRoom> data) {
                    uiState.setValue(new OtherChatRoomUiState(false, null, data));
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        } catch (Exception e) {
            uiState.setValue(new OtherChatRoomUiState(false, e.getMessage(), new ArrayList<>()));
        }
    }
}
