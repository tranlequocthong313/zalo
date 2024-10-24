package vn.edu.ou.zalo.ui.states;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;

public class ChatRoomUiState {
    private final List<ChatRoom> chatRooms;
    private final boolean isLoading;
    private final String errorMessage;

    public ChatRoomUiState(List<ChatRoom> chatRooms, boolean isLoading, String errorMessage) {
        this.chatRooms = chatRooms;
        this.isLoading = isLoading;
        this.errorMessage = errorMessage;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
