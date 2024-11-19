package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;

public class ChatUiState extends BaseUiState {
    private final List<Message> messages;
    private final ChatRoom chatRoom;
    private final User signedInUser;

    public ChatUiState(boolean isLoading, String errorMessage, List<Message> messages, ChatRoom chatRoom, User signedInUser) {
        super(isLoading, errorMessage);
        this.messages = messages;
        this.chatRoom = chatRoom;
        this.signedInUser = signedInUser;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public User getSignedInUser() {
        return signedInUser;
    }
}
