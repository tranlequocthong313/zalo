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
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.adapters.ChatRoomsAdapter;
import vn.edu.ou.zalo.ui.fragments.adapters.FriendRecommendationAdapter;
import vn.edu.ou.zalo.ui.fragments.listeners.OnFriendClickListener;
import vn.edu.ou.zalo.ui.states.ChatRoomUiState;
import vn.edu.ou.zalo.ui.states.FriendshipUiState;
import vn.edu.ou.zalo.ui.viewmodels.ChatRoomsViewModel;
import vn.edu.ou.zalo.ui.viewmodels.FriendshipViewModel;

@AndroidEntryPoint
public class FocusedChatRoomsFragment extends Fragment implements OnFriendClickListener {
    @Inject
    ChatRoomsViewModel chatRoomsViewModel;
    @Inject
    FriendshipViewModel friendshipViewModel;
    private FriendRecommendationAdapter friendRecommendationAdapter;
    private RecyclerView recyclerView;
    private RecyclerView friendRecommendationRecyclerView;
    private ChatRoomsAdapter chatRoomsAdapter;
    private View emptyView;
    private View mainContentView;

    public static FocusedChatRoomsFragment newInstance() {
        return new FocusedChatRoomsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_focused_chat_rooms, container, false);

        recyclerView = view.findViewById(R.id.fragment_chat_rooms_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);

        friendRecommendationRecyclerView = view.findViewById(R.id.fragment_chat_rooms_suggestion_recycler_view);
        friendRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        friendRecommendationRecyclerView.setFocusable(false);
        friendRecommendationRecyclerView.setNestedScrollingEnabled(false);

        emptyView = view.findViewById(R.id.fragment_focused_chat_rooms_empty_view);
        mainContentView = view.findViewById(R.id.fragment_focused_chat_rooms_view);

        chatRoomsViewModel.fetchData(ChatRoom.Priority.FOCUSED);
        chatRoomsViewModel.checkEmptyChatRoom();

        friendshipViewModel.fetchFriendRecommendations();

        chatRoomsViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);
        friendshipViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    private void updateUi(FriendshipUiState uiState) {
        if (uiState.isLoading()) {
            return;
        }

        List<User> friendSuggestions = uiState.getLimitedFriendRecommendations();
        if (uiState.getErrorMessage() != null) {
            Toast.makeText(getActivity(), uiState.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
        if (friendRecommendationRecyclerView.getAdapter() == null) {
            friendRecommendationAdapter = new FriendRecommendationAdapter(friendSuggestions, this);
            friendRecommendationRecyclerView.setAdapter(friendRecommendationAdapter);
        } else {
            friendRecommendationAdapter.updateFriendSuggestions(friendSuggestions);
        }
    }

    private void updateUi(ChatRoomUiState uiState) {
        if (uiState.isLoading()) {
            return;
        }

        if (uiState.isFocusedEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            mainContentView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            mainContentView.setVisibility(View.VISIBLE);
        }

        List<ChatRoom> chatRooms = uiState.getChatRooms();

        if (recyclerView.getAdapter() == null) {
            chatRoomsAdapter = new ChatRoomsAdapter(chatRooms);
            recyclerView.setAdapter(chatRoomsAdapter);
        } else {
            chatRoomsAdapter.updateChatRooms(chatRooms);
        }
    }

    @Override
    public void onAddFriendClick(User friend) {
        friendshipViewModel.addFriend(friend);
    }

    @Override
    public void onItemClick(User friend) {

    }
}