package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;

public class ChatRoomUiState extends BaseUiState {
    private final List<ChatRoom> chatRooms;
    private final boolean isFocusedEmpty;
    private final boolean isOtherEmpty;
    private final User signedInUser;

    public ChatRoomUiState(boolean isLoading, String errorMessage, List<ChatRoom> chatRooms, boolean isFocusedEmpty, boolean isOtherEmpty, User signedInUser) {
        super(isLoading, errorMessage);
        this.chatRooms = chatRooms;
        this.isFocusedEmpty = isFocusedEmpty;
        this.isOtherEmpty = isOtherEmpty;
        this.signedInUser = signedInUser;
    }

    public User getSignedInUser() {
        return signedInUser;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public boolean isFocusedEmpty() {
        return isFocusedEmpty;
    }

    public boolean isOtherEmpty() {
        return isOtherEmpty;
    }
}
