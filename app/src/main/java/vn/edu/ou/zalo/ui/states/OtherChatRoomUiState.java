package vn.edu.ou.zalo.ui.states;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;

public class OtherChatRoomUiState extends BaseUiState {
    private static final int MAX_CHAT_ROOM_RECOMMENDATIONS = 3;

    private final List<ChatRoom> otherRecommendedChatRooms;

    public OtherChatRoomUiState(boolean isLoading, String errorMessage, List<ChatRoom> otherRecommendedChatRooms) {
        super(isLoading, errorMessage);
        this.otherRecommendedChatRooms = otherRecommendedChatRooms;
    }

    public List<ChatRoom> getOtherRecommendedChatRooms() {
        return otherRecommendedChatRooms == null ? new ArrayList<>() : otherRecommendedChatRooms;
    }


    public List<ChatRoom> getLimitedChatRoomSuggestions() {
        return getLimitedChatRoomSuggestions(MAX_CHAT_ROOM_RECOMMENDATIONS);
    }

    public List<ChatRoom> getLimitedChatRoomSuggestions(int limit) {
        return otherRecommendedChatRooms != null ?
                otherRecommendedChatRooms.subList(0, Math.min(limit, otherRecommendedChatRooms.size())) :
                new ArrayList<>();
    }
}
