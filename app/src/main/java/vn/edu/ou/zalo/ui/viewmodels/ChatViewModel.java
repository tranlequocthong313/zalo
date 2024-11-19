package vn.edu.ou.zalo.ui.viewmodels;

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
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.GetDetailChatRoomByIdUseCase;
import vn.edu.ou.zalo.domain.impl.GetDetailChatRoomByUserUseCase;
import vn.edu.ou.zalo.domain.impl.GetSignedInUserUseCase;
import vn.edu.ou.zalo.ui.states.ChatUiState;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<ChatUiState> uiState =
            new MutableLiveData<>(new ChatUiState(false, null, null, null, null));
    private final IGetListUseCase<Message> getMessagesUseCase;
    private final GetSignedInUserUseCase getSignedInUserUseCase;
    private final GetDetailChatRoomByIdUseCase getDetailChatRoomUseCase;
    private final GetDetailChatRoomByUserUseCase getDetailChatRoomByUserUseCase;
    private List<Message> messages = new ArrayList<>();
    private ChatRoom chatRoom;
    private User signedInUser;

    @Inject
    public ChatViewModel(IGetListUseCase<Message> getMessagesUseCase, GetSignedInUserUseCase getSignedInUserUseCase, GetDetailChatRoomByIdUseCase getDetailChatRoomUseCase, GetDetailChatRoomByUserUseCase getDetailChatRoomByUserUseCase) {
        this.getMessagesUseCase = getMessagesUseCase;
        this.getSignedInUserUseCase = getSignedInUserUseCase;
        this.getDetailChatRoomUseCase = getDetailChatRoomUseCase;
        this.getDetailChatRoomByUserUseCase = getDetailChatRoomByUserUseCase;

        fetchData();
    }

    public LiveData<ChatUiState> getUiState() {
        return uiState;
    }

    public void fetchChatRoom(User user) {
        uiState.setValue(new ChatUiState(true, null, null, null, null));

        getDetailChatRoomByUserUseCase.execute(user, new IDomainCallback<ChatRoom>() {
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

    public void fetchMessages() {
        uiState.setValue(new ChatUiState(true, null, null, getChatRoom(), null));

        if (getChatRoom() == null || getChatRoom().getId() == null) {
            return;
        }
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
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }

    public void fetchData() {
        getSignedInUserUseCase.execute(new IDomainCallback<User>() {
            @Override
            public void onSuccess(User data) {
                signedInUser = data;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }

    private void updateUiState() {
        uiState.setValue(new ChatUiState(false, null, messages, chatRoom, signedInUser));
    }

    private ChatRoom getChatRoom() {
        return Objects.requireNonNull(uiState.getValue()).getChatRoom();
    }
}
