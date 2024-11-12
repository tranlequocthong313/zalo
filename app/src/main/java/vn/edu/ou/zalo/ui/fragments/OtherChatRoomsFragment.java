package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.ui.fragments.adapters.ChatRoomsAdapter;
import vn.edu.ou.zalo.ui.fragments.adapters.OtherChatRoomRecommendationAdapter;
import vn.edu.ou.zalo.ui.states.ChatRoomUiState;
import vn.edu.ou.zalo.ui.states.OtherChatRoomUiState;
import vn.edu.ou.zalo.ui.viewmodels.ChatRoomsViewModel;
import vn.edu.ou.zalo.ui.viewmodels.OtherChatRoomsRecommendationViewModel;

@AndroidEntryPoint
public class OtherChatRoomsFragment extends Fragment {
    @Inject
    ChatRoomsViewModel chatRoomsViewModel;
    @Inject
    OtherChatRoomsRecommendationViewModel otherChatRoomsRecommendationViewModel;
    private OtherChatRoomRecommendationAdapter otherChatRoomRecommendationAdapter;
    private RecyclerView recyclerView;
    private RecyclerView otherChatRoomRecommendationRecyclerView;
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

        otherChatRoomRecommendationRecyclerView = view.findViewById(R.id.fragment_chat_rooms_suggestion_recycler_view);
        otherChatRoomRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        otherChatRoomRecommendationRecyclerView.setFocusable(false);
        otherChatRoomRecommendationRecyclerView.setNestedScrollingEnabled(false);

        chatRoomsViewModel.fetchData(ChatRoom.Priority.OTHER);

        chatRoomsViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);
        otherChatRoomsRecommendationViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    private void updateUi(ChatRoomUiState uiState) {
        if (uiState.isLoading()) {
            return;
        }
        List<ChatRoom> chatRooms = uiState.getChatRooms();

        if (recyclerView.getAdapter() == null) {
            chatRoomsAdapter = new ChatRoomsAdapter(chatRooms);
            recyclerView.setAdapter(chatRoomsAdapter);
        } else {
            chatRoomsAdapter.updateChatRooms(chatRooms);
        }
    }

    private void updateUi(OtherChatRoomUiState uiState) {
        if (uiState.isLoading()) {
            return;
        }
        List<ChatRoom> recommendations = uiState.getOtherRecommendedChatRooms();

        if (recyclerView.getAdapter() == null) {
            otherChatRoomRecommendationAdapter = new OtherChatRoomRecommendationAdapter(recommendations);
            otherChatRoomRecommendationRecyclerView.setAdapter(otherChatRoomRecommendationAdapter);
        } else {
            otherChatRoomRecommendationAdapter.updateRecommendation(recommendations);
        }
    }
}