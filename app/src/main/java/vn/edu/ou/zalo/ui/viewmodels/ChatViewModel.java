package vn.edu.ou.zalo.ui.viewmodels;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.CreateChatRoomUseCase;
import vn.edu.ou.zalo.domain.impl.GetDetailChatRoomByIdUseCase;
import vn.edu.ou.zalo.domain.impl.GetDetailChatRoomByUserUseCase;
import vn.edu.ou.zalo.domain.impl.GetMessagesUseCase;
import vn.edu.ou.zalo.domain.impl.GetSignedInUserUseCase;
import vn.edu.ou.zalo.domain.impl.ListenMessageInChatRoomUseCase;
import vn.edu.ou.zalo.domain.impl.SendMessageUseCase;
import vn.edu.ou.zalo.ui.states.ChatUiState;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<ChatUiState> uiState =
            new MutableLiveData<>(new ChatUiState(false, null, null, null, null));
    private final GetMessagesUseCase getMessagesUseCase;
    private final GetSignedInUserUseCase getSignedInUserUseCase;
    private final GetDetailChatRoomByIdUseCase getDetailChatRoomUseCase;
    private final GetDetailChatRoomByUserUseCase getDetailChatRoomByUserUseCase;
    private final CreateChatRoomUseCase createChatRoomUseCase;
    private final SendMessageUseCase sendMessageUseCase;
    private final ListenMessageInChatRoomUseCase listenMessageInChatRoomUseCase;
    private List<Message> messages = new ArrayList<>();
    private ChatRoom chatRoom;
    private User signedInUser;

    @Inject
    public ChatViewModel(GetMessagesUseCase getMessagesUseCase, GetSignedInUserUseCase getSignedInUserUseCase, GetDetailChatRoomByIdUseCase getDetailChatRoomUseCase, GetDetailChatRoomByUserUseCase getDetailChatRoomByUserUseCase, CreateChatRoomUseCase createChatRoomUseCase, SendMessageUseCase sendMessageUseCase, ListenMessageInChatRoomUseCase listenMessageInChatRoomUseCase) {
        this.getMessagesUseCase = getMessagesUseCase;
        this.getSignedInUserUseCase = getSignedInUserUseCase;
        this.getDetailChatRoomUseCase = getDetailChatRoomUseCase;
        this.getDetailChatRoomByUserUseCase = getDetailChatRoomByUserUseCase;
        this.createChatRoomUseCase = createChatRoomUseCase;
        this.sendMessageUseCase = sendMessageUseCase;
        this.listenMessageInChatRoomUseCase = listenMessageInChatRoomUseCase;

        fetchSignedInUser();
    }
    public LiveData<ChatUiState> getUiState() {
        return uiState;
    }

    public void fetchChatRoom(User user) {
        uiState.setValue(new ChatUiState(true, null, null, null, null));

        getDetailChatRoomByUserUseCase.execute(user, new IDomainCallback<ChatRoom>() {
            @Override
            public void onSuccess(ChatRoom data) {
                if (data == null) {
                    ChatRoom c = new ChatRoom();
                    Set<ChatRoom.Member> members = new HashSet<>();
                    members.add(ChatRoom.Member.fromUser(user));
                    members.add(ChatRoom.Member.fromUser(Objects.requireNonNull(uiState.getValue()).getSignedInUser()));
                    c.setMembers(members);
                    c.setType(ChatRoom.Type.SINGLE);
                    createChatRoomUseCase.execute(c, new IDomainCallback<ChatRoom>() {
                        @Override
                        public void onSuccess(ChatRoom data) {
                            chatRoom = data;
                            updateUiState();
                            listenMessage();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
                        }
                    });
                } else {
                    chatRoom = data;
                    updateUiState();
                    listenMessage();
                }
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }

    public void fetchChatRoom(String chatRoomId) {
        uiState.setValue(new ChatUiState(true, null, null, null, null));

        getDetailChatRoomUseCase.execute(chatRoomId, new IDomainCallback<ChatRoom>() {
            @Override
            public void onSuccess(ChatRoom data) {
                chatRoom = data;
                updateUiState();
                listenMessage();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }

    public void fetchMessages() {
        if (getChatRoom() == null) {
            return;
        }
        getMessagesUseCase.execute(getChatRoom().getId(), new IDomainCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> data) {
                messages = data;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }

    public void fetchSignedInUser() {
        getSignedInUserUseCase.execute(new IDomainCallback<User>() {
            @Override
            public void onSuccess(User data) {
                signedInUser = data;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }

    public void sendMessage(String message) {
        Message m = new Message();
        m.setChatRoomId(Objects.requireNonNull(uiState.getValue()).getChatRoom().getId());
        m.setSenderId(uiState.getValue().getSignedInUser().getId());
        m.setTextContent(message);
        m.setType(Message.Type.TEXT);
        m.setSender(uiState.getValue().getSignedInUser());

        sendMessageUseCase.execute(m, new IDomainCallback<Message>() {
            @Override
            public void onSuccess(Message data) {
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }

    public void listenMessage() {
        if (getChatRoom() == null) {
            return;
        }

        listenMessageInChatRoomUseCase.execute(getChatRoom().getId(), new IDomainCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> data) {
                List<Message> newMessages = new ArrayList<>(data);
                newMessages.addAll(messages);
                newMessages.sort((o1, o2) -> (int) -(o1.getCreatedAt() - o2.getCreatedAt()));
                messages = newMessages;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }

    private void updateUiState() {
        uiState.setValue(new ChatUiState(false, null, messages, chatRoom, signedInUser));
    }

    private ChatRoom getChatRoom() {
        return Objects.requireNonNull(uiState.getValue()).getChatRoom();
    }

    public void sendMessage(Uri uri) {
        Message m = new Message();
        m.setChatRoomId(Objects.requireNonNull(uiState.getValue()).getChatRoom().getId());
        m.setSenderId(uiState.getValue().getSignedInUser().getId());
        m.setFileUris(List.of(uri));
        m.setType(Message.Type.IMAGE);
        m.setSender(uiState.getValue().getSignedInUser());

        sendMessageUseCase.execute(m, new IDomainCallback<Message>() {
            @Override
            public void onSuccess(Message data) {
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new ChatUiState(false, e.getMessage(), null, null, null));
            }
        });
    }
}
