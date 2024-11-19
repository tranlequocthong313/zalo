package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.ui.fragments.adapters.ReceivedFriendRequestAdapter;
import vn.edu.ou.zalo.ui.fragments.listeners.OnReceivedFriendRequestClickListener;
import vn.edu.ou.zalo.ui.states.FriendRequestUiState;
import vn.edu.ou.zalo.ui.viewmodels.FriendRequestViewModel;

@AndroidEntryPoint
public class ReceivedFriendRequestFragment extends Fragment implements OnReceivedFriendRequestClickListener {
    @Inject
    FriendRequestViewModel friendRequestViewModel;
    private RecyclerView recyclerView;
    private ReceivedFriendRequestAdapter adapter;
    private TextView emptyTextView;

    public static ReceivedFriendRequestFragment newInstance() {
        return new ReceivedFriendRequestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_received_friend_request, container, false);

        friendRequestViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);
        friendRequestViewModel.getReceivedFriendRequests();

        emptyTextView = view.findViewById(R.id.fragment_received_friend_request_empty_text_view);

        recyclerView = view.findViewById(R.id.fragment_received_friend_request_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void updateUi(FriendRequestUiState friendRequestUiState) {
        if (friendRequestUiState.isLoading()) {
            return;
        }

        List<Friendship> friendships = friendRequestUiState.getReceivedRequests();

        if (friendships == null || friendships.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        if (recyclerView.getAdapter() == null) {
            adapter = new ReceivedFriendRequestAdapter(friendships, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateFriendships(friendships);
        }
    }

    @Override
    public void onRefuse(Friendship friendship) {
        friendRequestViewModel.refuseFriendRequest(friendship);
    }

    @Override
    public void onAccept(Friendship friendship) {
        friendRequestViewModel.acceptFriendRequest(friendship);
    }
}