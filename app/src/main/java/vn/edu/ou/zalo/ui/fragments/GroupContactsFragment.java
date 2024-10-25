package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import vn.edu.ou.zalo.ui.states.GroupChatRoomUiState;
import vn.edu.ou.zalo.ui.viewmodels.FocusedChatRoomsViewModel;
import vn.edu.ou.zalo.ui.viewmodels.GroupChatRoomsViewModel;

@AndroidEntryPoint
public class GroupContactsFragment extends Fragment {

    @Inject
    GroupChatRoomsViewModel groupChatRoomsViewModel;
    private RecyclerView recyclerView;
    private ChatRoomsAdapter chatRoomsAdapter;
    private TextView joinedGroupTextView;

    public static GroupContactsFragment newInstance() {
        return new GroupContactsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_contacts, container, false);

        joinedGroupTextView = view.findViewById(R.id.fragment_group_contacts_joined_groups_text);

        recyclerView = view.findViewById(R.id.fragment_group_contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);

        groupChatRoomsViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    private void updateUi(GroupChatRoomUiState uiState) {
        List<ChatRoom> groupChats = uiState.getChatRooms();

        String groupCount = String.format(getString(R.string.joined_groups), groupChats.size());
        joinedGroupTextView.setText(groupCount);

        if (recyclerView.getAdapter() == null) {
            chatRoomsAdapter = new ChatRoomsAdapter(groupChats);
            recyclerView.setAdapter(chatRoomsAdapter);
        } else {
            chatRoomsAdapter.updateChatRooms(groupChats);
        }
    }
}