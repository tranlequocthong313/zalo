package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;

public class OtherChatRoomUiState extends BaseUiState {
    private final List<ChatRoom> chatRooms;
    private final List<ChatRoom> otherChatRooms;

    public OtherChatRoomUiState(boolean isLoading, String errorMessage, List<ChatRoom> chatRooms, List<ChatRoom> otherChatRooms) {
        super(isLoading, errorMessage);
        this.chatRooms = chatRooms;
        this.otherChatRooms = otherChatRooms;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public List<ChatRoom> getOtherChatRooms() {
        return otherChatRooms;
    }
}
