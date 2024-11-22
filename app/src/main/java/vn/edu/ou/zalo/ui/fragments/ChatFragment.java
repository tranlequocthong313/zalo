package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.adapters.ChatAdapter;
import vn.edu.ou.zalo.ui.states.ChatUiState;
import vn.edu.ou.zalo.ui.states.FriendshipUiState;
import vn.edu.ou.zalo.ui.viewmodels.ChatViewModel;
import vn.edu.ou.zalo.ui.viewmodels.FriendshipViewModel;
import vn.edu.ou.zalo.utils.TimeUtils;

@AndroidEntryPoint
public class ChatFragment extends Fragment {
    private static final String ARGS_CHAT_ROOM_ID = "chat_room_id";
    private static final String ARGS_USER = "user";

    @Inject
    ChatViewModel chatViewModel;
    @Inject
    FriendshipViewModel friendshipViewModel;

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private MaterialToolbar toolbar;
    private View addFriendView;
    private User user;
    private String message;
    private boolean checkedFriendStatus;

    public static ChatFragment newInstance(String chatRoomId) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_CHAT_ROOM_ID, chatRoomId);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ChatFragment newInstance(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_USER, user);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        toolbar = view.findViewById(R.id.fragment_chat_top_app_bar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());

        addFriendView = view.findViewById(R.id.fragment_chat_add_friend_layout);

        recyclerView = view.findViewById(R.id.fragment_chat_messages_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        TextInputEditText messageInput = view.findViewById(R.id.fragment_chat_message_input);
        messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                message = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            if (message == null || message.isEmpty()) {
                return true;
            }
            if (actionId == EditorInfo.IME_ACTION_GO) {
                chatViewModel.sendMessage(message);
                messageInput.setText("");
                return true;
            }
            return false;
        });

        assert getArguments() != null;
        String chatRoomId = getArguments().getString(ARGS_CHAT_ROOM_ID);
        user = getArguments().getParcelable(ARGS_USER);
        if (chatRoomId != null) {
            chatViewModel.fetchChatRoom(chatRoomId);
        } else if (user != null) {
            chatViewModel.fetchChatRoom(user);
            friendshipViewModel.checkFriendStatus(user);
            checkedFriendStatus = true;
            toolbar.setTitle(user.getFullName());
            String onlineStatus = String.format(getString(R.string.last_seen), TimeUtils.getDetailedTimeAgo(user.getLastLogin()));
            toolbar.setSubtitle(onlineStatus);
        }

        chatViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);
        friendshipViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    private void updateUi(FriendshipUiState friendshipUiState) {
        if (friendshipUiState.isLoading()) {
            return;
        }
        TextView textView = addFriendView.findViewById(R.id.fragment_chat_add_friend_layout_text_view);

        if (friendshipUiState.getStatus() == Friendship.Status.ACCEPTED || friendshipUiState.getStatus() == Friendship.Status.BLOCKED) {
            addFriendView.setVisibility(View.GONE);
        } else if (friendshipUiState.getStatus() == Friendship.Status.PENDING) {
            addFriendView.setVisibility(View.VISIBLE);
            textView.setText(R.string.friend_request_has_been_sent);
        } else {
            addFriendView.setVisibility(View.VISIBLE);
            textView.setText(R.string.add_friend);
            addFriendView.setOnClickListener(v -> friendshipViewModel.sendFriendRequest(user));
        }
    }

    private void updateUi(ChatUiState chatUiState) {
        if (chatUiState.isLoading()) {
            return;
        }

        List<Message> messages = chatUiState.getMessages();
        ChatRoom chatRoom = chatUiState.getChatRoom();

        if (chatRoom == null) {
            return;
        }

        if (chatRoom.isGroupChat()) {
            toolbar.setTitle(chatRoom.getName());
            toolbar.setSubtitle(R.string.tab_for_more_info);
        } else {
            ChatRoom.Member other = chatRoom.getOtherMember(chatUiState.getSignedInUser());
            if (!checkedFriendStatus) {
                friendshipViewModel.checkFriendStatus(other.getUser());
                checkedFriendStatus = true;
            }
            toolbar.setTitle(other.getUser().getFullName());
            String onlineStatus = String.format(getString(R.string.last_seen), TimeUtils.getDetailedTimeAgo(other.getUser().getLastLogin()));
            toolbar.setSubtitle(onlineStatus);
        }

        if (recyclerView.getAdapter() == null) {
            chatAdapter = new ChatAdapter(messages, chatUiState.getSignedInUser());
            recyclerView.setAdapter(chatAdapter);
        } else {
            chatAdapter.updateMessages(messages);
        }
    }
}