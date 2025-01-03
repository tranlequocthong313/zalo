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
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.activities.ChatActivity;
import vn.edu.ou.zalo.ui.activities.FriendRequestActivity;
import vn.edu.ou.zalo.ui.fragments.adapters.FriendContactAdapter;
import vn.edu.ou.zalo.ui.fragments.listeners.OnFriendClickListener;
import vn.edu.ou.zalo.ui.states.FriendContactsUiState;
import vn.edu.ou.zalo.ui.viewmodels.FriendContactsViewModel;

@AndroidEntryPoint
public class FriendContactsFragment extends Fragment implements OnFriendClickListener {
    @Inject
    FriendContactsViewModel friendContactsViewModel;
    private RecyclerView recyclerView;
    private FriendContactAdapter adapter;
    private Button allFriendsButton;
    private Button onlineFriendsButton;

    public static FriendContactsFragment newInstance() {
        return new FriendContactsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_contacts, container, false);

        View friendRequest = view.findViewById(R.id.fragment_friend_contacts_friend_request);
        friendRequest.setOnClickListener(v -> startActivity(FriendRequestActivity.newIntent(getActivity())));

        allFriendsButton = view.findViewById(R.id.fragment_friend_contacts_all_friends_button);
        onlineFriendsButton = view.findViewById(R.id.fragment_friend_contacts_online_friends_button);

        recyclerView = view.findViewById(R.id.fragment_friend_contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);

        friendContactsViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        friendContactsViewModel.fetchFriends();
    }

    private void updateUi(FriendContactsUiState uiState) {
        if (uiState.isLoading()) {
            return;
        }
        List<User> friends = uiState.getFriends();
        List<User> onlineFriends = uiState.getOnlineFriends();
        if (friends == null) {
            return;
        }
        String allFriendCount = String.format(
                getResources().getString(R.string.all),
                friends.size()
        );
        if (onlineFriends != null) {
            String recentlyOnlineCount = String.format(
                    getResources().getString(R.string.recently_online),
                    onlineFriends.size()
            );
            onlineFriendsButton.setText(recentlyOnlineCount);
        }
        allFriendsButton.setText(allFriendCount);

        if (recyclerView.getAdapter() == null) {
            adapter = new FriendContactAdapter(friends, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateFriends(friends);
        }
    }

    @Override
    public void onAddFriendClick(User friend) {

    }

    @Override
    public void onItemClick(User friend) {
        startActivity(ChatActivity.newIntent(getActivity(), friend));
    }
}