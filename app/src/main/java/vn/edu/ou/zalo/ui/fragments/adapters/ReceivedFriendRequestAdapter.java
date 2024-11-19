package vn.edu.ou.zalo.ui.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.ui.fragments.listeners.OnReceivedFriendRequestClickListener;
import vn.edu.ou.zalo.ui.fragments.viewholders.ReceivedFriendRequestViewHolder;

public class ReceivedFriendRequestAdapter extends RecyclerView.Adapter<ReceivedFriendRequestViewHolder> {
    private List<Friendship> friendships;
    private final OnReceivedFriendRequestClickListener listener;

    public ReceivedFriendRequestAdapter(List<Friendship> friendships, OnReceivedFriendRequestClickListener listener) {
        this.friendships = friendships;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReceivedFriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_received_friend_request, parent, false);
        return new ReceivedFriendRequestViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedFriendRequestViewHolder holder, int position) {
        holder.bindFriendRequest(friendships.get(position));
    }

    @Override
    public int getItemCount() {
        return friendships.size();
    }

    public void updateFriendships(List<Friendship> friendships) {
        if (friendships == null) {
            return;
        }

        this.friendships = friendships;
        notifyDataSetChanged();
    }
}

