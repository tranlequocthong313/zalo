package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.adapters.ChatAdapter;
import vn.edu.ou.zalo.ui.states.ChatUiState;
import vn.edu.ou.zalo.ui.viewmodels.ChatViewModel;
import vn.edu.ou.zalo.utils.TimeUtils;

@AndroidEntryPoint
public class ChatFragment extends Fragment {
    private static final String ARGS_CHAT_ROOM_ID = "chat_room_id";

    @Inject
    ChatViewModel chatViewModel;

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private MaterialToolbar toolbar;

    public static ChatFragment newInstance(String chatRoomId) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_CHAT_ROOM_ID, chatRoomId);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        toolbar = view.findViewById(R.id.chat_activity_top_app_bar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());

        recyclerView = view.findViewById(R.id.fragment_chat_messages_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        assert getArguments() != null;
        String chatRoomId = getArguments().getString(ARGS_CHAT_ROOM_ID);
        chatViewModel.fetchChatRoom(chatRoomId);
        chatViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);
        chatViewModel.fetchData();

        return view;
    }

    private void updateUi(ChatUiState chatUiState) {
        List<Message> messages = chatUiState.getMessages();
        User loginUser = chatUiState.getLoginUser();
        ChatRoom chatRoom = chatUiState.getChatRoom();

        toolbar.setTitle(chatRoom.getName());
        if (chatRoom.isGroupChat()) {
            toolbar.setSubtitle(R.string.tab_for_more_info);
        } else {
            ChatRoom.Member otherMember = chatRoom.getOtherMember(loginUser);
            String onlineStatus = String.format(getString(R.string.last_seen), TimeUtils.getDetailedTimeAgo(otherMember.getUser().getLastLogin()));
            toolbar.setSubtitle(onlineStatus);
        }

        if (recyclerView.getAdapter() == null) {
            chatAdapter = new ChatAdapter(messages, loginUser);
            recyclerView.setAdapter(chatAdapter);
        } else {
            chatAdapter.updateMessages(messages);
        }
    }
}