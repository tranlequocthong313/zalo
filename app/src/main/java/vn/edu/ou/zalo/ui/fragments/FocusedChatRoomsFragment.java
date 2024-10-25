package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.adapters.ChatRoomsAdapter;
import vn.edu.ou.zalo.ui.fragments.adapters.FriendSuggestionAdapter;
import vn.edu.ou.zalo.ui.states.FocusedChatRoomUiState;
import vn.edu.ou.zalo.ui.viewmodels.FocusedChatRoomsViewModel;

@AndroidEntryPoint
public class FocusedChatRoomsFragment extends Fragment {
    @Inject
    FocusedChatRoomsViewModel focusedChatRoomsViewModel;
    private FriendSuggestionAdapter friendSuggestionAdapter;
    private RecyclerView recyclerView;
    private RecyclerView friendSuggestionRecyclerView;
    private ChatRoomsAdapter chatRoomsAdapter;

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

        friendSuggestionRecyclerView = view.findViewById(R.id.fragment_chat_rooms_suggestion_recycler_view);
        friendSuggestionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        friendSuggestionRecyclerView.setFocusable(false);
        friendSuggestionRecyclerView.setNestedScrollingEnabled(false);

        focusedChatRoomsViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState.isLoading()) {
                // TODO: Show loading indicator
                Log.d("FocusedChatRoom", "Loading");
            } else {
                // TODO: Hide loading indicator
                if (uiState.getErrorMessage() != null) {
                    // TODO: Show error message
                    Log.d("FocusedChatRoom", "Err");
                    Toast.makeText(getActivity(), uiState.getErrorMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("FocusedChatRoom", "Update");
                    updateUi(uiState);
                }
            }
        });
        Log.d("FocusedChatRoom", "onCreateView");

        return view;
    }

    private void updateUi(FocusedChatRoomUiState uiState) {
        List<ChatRoom> chatRooms = uiState.getChatRooms() != null ? uiState.getChatRooms() : new ArrayList<>();
        List<User> friendSuggestions = uiState.getFriendSuggestions() != null ? uiState.getFriendSuggestions() : new ArrayList<>();

        Log.d("FocusedChatRoom", String.valueOf(chatRooms.size()));
        Log.d("FocusedChatRoom", String.valueOf(friendSuggestions.size()));

        if (recyclerView.getAdapter() == null) {
            chatRoomsAdapter = new ChatRoomsAdapter(chatRooms);
            friendSuggestionAdapter = new FriendSuggestionAdapter(friendSuggestions);
            recyclerView.setAdapter(chatRoomsAdapter);
            friendSuggestionRecyclerView.setAdapter(friendSuggestionAdapter);
        } else {
            chatRoomsAdapter.updateChatRooms(chatRooms);
            friendSuggestionAdapter.updateFriendSuggestions(friendSuggestions);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("FocusedChatRoom", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("FocusedChatRoom", "onDestroy");
    }
}