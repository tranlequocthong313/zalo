package vn.edu.ou.zalo.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.GetSignedInUserUseCase;
import vn.edu.ou.zalo.ui.states.ChatUiState;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<ChatUiState> uiState =
            new MutableLiveData<>(new ChatUiState(false, null, null, null, null));
    private final IGetListUseCase<Message> getMessagesUseCase;
    private final GetSignedInUserUseCase getSignedInUserUseCase;
    private final IGetDetailUseCase<ChatRoom> getDetailChatRoomUseCase;
    private User loginUser;
    private List<Message> messages = new ArrayList<>();
    private ChatRoom chatRoom;

    @Inject
    public ChatViewModel(IGetListUseCase<Message> getMessagesUseCase, GetSignedInUserUseCase getSignedInUserUseCase, IGetDetailUseCase<ChatRoom> getDetailChatRoomUseCase) {
        this.getMessagesUseCase = getMessagesUseCase;
        this.getSignedInUserUseCase = getSignedInUserUseCase;
        this.getDetailChatRoomUseCase = getDetailChatRoomUseCase;
    }

    public LiveData<ChatUiState> getUiState() {
        return uiState;
    }

    public void fetchChatRoom(String chatRoomId) {
        uiState.setValue(new ChatUiState(true, null, null, null, null));

        getDetailChatRoomUseCase.execute(chatRoomId, new IDomainCallback<ChatRoom>() {
            @Override
            public void onSuccess(ChatRoom data) {
                chatRoom = data;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }

    public void fetchData() {
        uiState.setValue(new ChatUiState(true, null, null, null, getChatRoom()));

        try {
            Map<String, String> query = new HashMap<>();
            query.put("chatRoomId", getChatRoom().getId());
            getMessagesUseCase.execute(query, new IDomainCallback<List<Message>>() {
                @Override
                public void onSuccess(List<Message> data) {
                    messages = data;
                    updateUiState();
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
            loginUser = getSignedInUserUseCase.execute();
            updateUiState();
        } catch (Exception e) {
            uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, getChatRoom()));
        }
    }

    private void updateUiState() {
        uiState.setValue(new ChatUiState(false, null, messages, loginUser, chatRoom));
    }

    @NonNull
    private List<Message> getMessages() {
        return Objects.requireNonNull(Objects.requireNonNull(uiState.getValue()).getMessages());
    }

    private User getLoginUser() {
        return Objects.requireNonNull(uiState.getValue()).getLoginUser();
    }

    private ChatRoom getChatRoom() {
        return Objects.requireNonNull(uiState.getValue()).getChatRoom();
    }
}
