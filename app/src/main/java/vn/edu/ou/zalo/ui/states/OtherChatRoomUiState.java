package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;

public class OtherChatRoomUiState extends BaseUiState {
    private final List<ChatRoom> chatRooms;
    private final List<ChatRoom> unimportantChatRooms;

    public OtherChatRoomUiState(boolean isLoading, String errorMessage, List<ChatRoom> chatRooms, List<ChatRoom> unimportantChatRooms) {
        super(isLoading, errorMessage);
        this.chatRooms = chatRooms;
        this.unimportantChatRooms = unimportantChatRooms;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public List<ChatRoom> getUnimportantChatRooms() {
        return unimportantChatRooms;
    }
}
