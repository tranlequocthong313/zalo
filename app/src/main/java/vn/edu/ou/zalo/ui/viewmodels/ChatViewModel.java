package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.ChatUiState;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<ChatUiState> uiState =
            new MutableLiveData<>(new ChatUiState(false, null, null, null, null));
    private final IGetListUseCase<Message> getMessagesUseCase;
    private final IGetDetailUseCase<User> getLoginUserUseCase;
    private final IGetDetailUseCase<ChatRoom> getDetailChatRoomUseCase;

    @Inject
    public ChatViewModel(IGetListUseCase<Message> getMessagesUseCase, IGetDetailUseCase<User> getLoginUserUseCase, IGetDetailUseCase<ChatRoom> getDetailChatRoomUseCase) {
        this.getMessagesUseCase = getMessagesUseCase;
        this.getLoginUserUseCase = getLoginUserUseCase;
        this.getDetailChatRoomUseCase = getDetailChatRoomUseCase;
    }

    public LiveData<ChatUiState> getUiState() {
        return uiState;
    }

    public void fetchChatRoom(String chatRoomId) {
        ChatRoom chatRoom = getDetailChatRoomUseCase.execute(chatRoomId);
        uiState.setValue(new ChatUiState(false, null, null, null, chatRoom));
    }

    private ChatRoom getChatRoom() {
        return Objects.requireNonNull(uiState.getValue()).getChatRoom();
    }

    public void fetchData() {
        uiState.setValue(new ChatUiState(true, null, null, null, getChatRoom()));

        try {
            Map<String, String> query = new HashMap<>();
            query.put("chatRoomId", getChatRoom().getId());
            List<Message> messages = getMessagesUseCase.execute(query);
            User loginUser = getLoginUserUseCase.execute();
            uiState.setValue(new ChatUiState(false, null, messages, loginUser, getChatRoom()));
        } catch (Exception e) {
            uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, getChatRoom()));
        }
    }
}
