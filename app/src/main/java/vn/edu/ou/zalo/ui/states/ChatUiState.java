package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;

public class ChatUiState extends BaseUiState {
    private final List<Message> messages;
    private final User loginUser;
    private final ChatRoom chatRoom;

    public ChatUiState(boolean isLoading, String errorMessage, List<Message> messages, User loginUser, ChatRoom chatRoomId) {
        super(isLoading, errorMessage);
        this.messages = messages;
        this.loginUser = loginUser;
        this.chatRoom = chatRoomId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }
}
