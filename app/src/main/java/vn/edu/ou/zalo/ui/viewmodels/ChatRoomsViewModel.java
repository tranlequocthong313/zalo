package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.GetChatRoomsUseCase;
import vn.edu.ou.zalo.domain.impl.GetSignedInUserUseCase;
import vn.edu.ou.zalo.domain.impl.ListenChatRoomsUseCase;
import vn.edu.ou.zalo.ui.states.ChatRoomUiState;

public class ChatRoomsViewModel extends ViewModel {
    private final MutableLiveData<ChatRoomUiState> uiState =
            new MutableLiveData<>(new ChatRoomUiState(false, null, new ArrayList<>(), false, false, null));
    private final GetChatRoomsUseCase getChatRoomsUseCase;
    private final ListenChatRoomsUseCase listenChatRoomsUseCase;
    private final GetSignedInUserUseCase getSignedInUserUseCase;

    @Inject
    public ChatRoomsViewModel(GetChatRoomsUseCase getChatRoomsUseCase, ListenChatRoomsUseCase listenChatRoomsUseCase, GetSignedInUserUseCase getSignedInUserUseCase) {
        this.getChatRoomsUseCase = getChatRoomsUseCase;
        this.listenChatRoomsUseCase = listenChatRoomsUseCase;
        this.getSignedInUserUseCase = getSignedInUserUseCase;

        getSignedInUser();
    }

    public void getSignedInUser() {
        uiState.setValue(new ChatRoomUiState(true, null, Objects.requireNonNull(uiState.getValue()).getChatRooms(), uiState.getValue().isFocusedEmpty(), uiState.getValue().isOtherEmpty(), uiState.getValue().getSignedInUser()));
        getSignedInUserUseCase.execute(new IDomainCallback<User>() {
            @Override
            public void onSuccess(User user) {
                uiState.setValue(new ChatRoomUiState(false, null, Objects.requireNonNull(uiState.getValue()).getChatRooms(), uiState.getValue().isFocusedEmpty(), uiState.getValue().isOtherEmpty(), user));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatRoomUiState(false, e.getMessage(), Objects.requireNonNull(uiState.getValue()).getChatRooms(), uiState.getValue().isFocusedEmpty(), uiState.getValue().isOtherEmpty(), null));
            }
        });
    }

    public LiveData<ChatRoomUiState> getUiState() {
        return uiState;
    }

    public void fetchData(ChatRoom.Priority priority) {
        uiState.setValue(new ChatRoomUiState(true, null, new ArrayList<>(), Objects.requireNonNull(uiState.getValue()).isFocusedEmpty(), uiState.getValue().isOtherEmpty(), uiState.getValue().getSignedInUser()));

        getChatRoomsUseCase.execute(Map.of("priority", priority.name()), new IDomainCallback<List<ChatRoom>>() {
            @Override
            public void onSuccess(List<ChatRoom> chatRooms) {
                uiState.setValue(new ChatRoomUiState(false, null, chatRooms, uiState.getValue().isFocusedEmpty(), uiState.getValue().isOtherEmpty(), uiState.getValue().getSignedInUser()));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatRoomUiState(false, e.getMessage(), new ArrayList<>(), uiState.getValue().isFocusedEmpty(), uiState.getValue().isOtherEmpty(), uiState.getValue().getSignedInUser()));
            }
        });
    }

    public void listenChatRoom() {
        listenChatRoom(null);
    }

    public void listenChatRoom(ChatRoom.Priority priority) {
        listenChatRoomsUseCase.execute(new IDomainCallback<List<ChatRoom>>() {
            @Override
            public void onSuccess(List<ChatRoom> chatRooms) {
                if (chatRooms.isEmpty() && !Objects.requireNonNull(uiState.getValue()).getChatRooms().isEmpty()) {
                    return;
                }

                Map<ChatRoom.Priority, Boolean> isEmptyMap = new HashMap<>();
                isEmptyMap.put(ChatRoom.Priority.FOCUSED, true);
                isEmptyMap.put(ChatRoom.Priority.OTHER, true);

                for (ChatRoom c : chatRooms) {
                    if (c.getPriority() == ChatRoom.Priority.FOCUSED) {
                        isEmptyMap.put(ChatRoom.Priority.FOCUSED, false);
                    } else if (c.getPriority() == ChatRoom.Priority.OTHER) {
                        isEmptyMap.put(ChatRoom.Priority.OTHER, false);
                    }
                }

                List<ChatRoom> newChatRooms = new ArrayList<>();
                if (priority != null) {
                    chatRooms = chatRooms.stream().filter(chatRoom -> chatRoom.getPriority() == priority).collect(Collectors.toList());
                }
                chatRooms.addAll(Objects.requireNonNull(uiState.getValue()).getChatRooms());
                Set<String> s = new HashSet<>();
                for (ChatRoom c : chatRooms) {
                    if (!s.contains(c.getId())) {
                        s.add(c.getId());
                        newChatRooms.add(c);
                    }
                }

                uiState.setValue(new ChatRoomUiState(false, null, newChatRooms, Boolean.TRUE.equals(isEmptyMap.get(ChatRoom.Priority.FOCUSED)), Boolean.TRUE.equals(isEmptyMap.get(ChatRoom.Priority.OTHER)), Objects.requireNonNull(uiState.getValue()).getSignedInUser()));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatRoomUiState(false, e.getMessage(), new ArrayList<>(), false, false, Objects.requireNonNull(uiState.getValue()).getSignedInUser()));
            }
        });
    }
}
