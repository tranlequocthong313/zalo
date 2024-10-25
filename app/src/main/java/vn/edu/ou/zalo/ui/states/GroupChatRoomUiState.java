package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;

public class GroupChatRoomUiState extends BaseUiState {
    private final List<ChatRoom> chatRooms;

    public GroupChatRoomUiState(boolean isLoading, String errorMessage, List<ChatRoom> chatRooms) {
        super(isLoading, errorMessage);
        this.chatRooms = chatRooms;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }
}
