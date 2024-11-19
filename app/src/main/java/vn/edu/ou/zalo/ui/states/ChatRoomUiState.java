package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;

public class ChatRoomUiState extends BaseUiState {
    private final List<ChatRoom> chatRooms;
    private final boolean isFocusedEmpty;
    private final boolean isOtherEmpty;

    public ChatRoomUiState(boolean isLoading, String errorMessage, List<ChatRoom> chatRooms, boolean isFocusedEmpty, boolean isOtherEmpty) {
        super(isLoading, errorMessage);
        this.chatRooms = chatRooms;
        this.isFocusedEmpty = isFocusedEmpty;
        this.isOtherEmpty = isOtherEmpty;
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
