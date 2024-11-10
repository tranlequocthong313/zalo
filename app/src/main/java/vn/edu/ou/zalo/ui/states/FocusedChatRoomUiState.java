package vn.edu.ou.zalo.ui.states;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;

public class FocusedChatRoomUiState extends BaseUiState {
    private final List<ChatRoom> chatRooms;
    private final List<User> friendSuggestions;

    public FocusedChatRoomUiState(boolean isLoading, String errorMessage, List<ChatRoom> chatRooms, List<User> friendSuggestions) {
        super(isLoading, errorMessage);
        this.chatRooms = chatRooms;
        this.friendSuggestions = friendSuggestions;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public List<User> getFriendSuggestions() {
        return friendSuggestions;
    }
}
