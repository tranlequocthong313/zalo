package vn.edu.ou.zalo.ui.states;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;

public class GroupChatRoomUiState extends BaseUiState {
    private final List<ChatRoom> chatRooms;
    private final User signedInUser;

    public GroupChatRoomUiState(boolean isLoading, String errorMessage, List<ChatRoom> chatRooms, User signedInUser) {
        super(isLoading, errorMessage);
        this.chatRooms = chatRooms;
        this.signedInUser = signedInUser;
    }

    public User getSignedInUser() {
        return signedInUser;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms == null ? new ArrayList<>() : chatRooms;
    }
}
