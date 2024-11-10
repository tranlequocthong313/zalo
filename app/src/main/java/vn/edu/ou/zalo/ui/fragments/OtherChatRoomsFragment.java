package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.ui.fragments.adapters.ChatRoomsAdapter;
import vn.edu.ou.zalo.ui.fragments.adapters.UnimportantChatRoomSuggestionAdapter;
import vn.edu.ou.zalo.ui.states.OtherChatRoomUiState;
import vn.edu.ou.zalo.ui.viewmodels.OtherChatRoomsViewModel;

@AndroidEntryPoint
public class OtherChatRoomsFragment extends Fragment {
    @Inject
    OtherChatRoomsViewModel otherChatRoomsViewModel;
    private UnimportantChatRoomSuggestionAdapter unimportantChatRoomSuggestionAdapter;
    private RecyclerView recyclerView;
    private RecyclerView unimportantChatRoomSuggestionRecyclerView;
    private ChatRoomsAdapter chatRoomsAdapter;

    public static OtherChatRoomsFragment newInstance() {
        return new OtherChatRoomsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_chat_rooms, container, false);

        recyclerView = view.findViewById(R.id.fragment_chat_rooms_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);

        unimportantChatRoomSuggestionRecyclerView = view.findViewById(R.id.fragment_chat_rooms_suggestion_recycler_view);
        unimportantChatRoomSuggestionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        unimportantChatRoomSuggestionRecyclerView.setFocusable(false);
        unimportantChatRoomSuggestionRecyclerView.setNestedScrollingEnabled(false);

        otherChatRoomsViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    private void updateUi(OtherChatRoomUiState uiState) {
        if (uiState.isLoading()) {
            return;
        }
        List<ChatRoom> chatRooms = uiState.getChatRooms();
        List<ChatRoom> suggestions = uiState.getOtherChatRooms();

        if (recyclerView.getAdapter() == null) {
            chatRoomsAdapter = new ChatRoomsAdapter(chatRooms);
            unimportantChatRoomSuggestionAdapter = new UnimportantChatRoomSuggestionAdapter(suggestions);
            recyclerView.setAdapter(chatRoomsAdapter);
            unimportantChatRoomSuggestionRecyclerView.setAdapter(unimportantChatRoomSuggestionAdapter);
        } else {
            chatRoomsAdapter.updateChatRooms(chatRooms);
            unimportantChatRoomSuggestionAdapter.updateSuggestion(suggestions);
        }
    }
}