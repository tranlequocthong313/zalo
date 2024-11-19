package vn.edu.ou.zalo.ui.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.ui.fragments.listeners.OnSentFriendRequestClickListener;
import vn.edu.ou.zalo.ui.fragments.viewholders.SentFriendRequestViewHolder;

public class SentFriendRequestAdapter extends RecyclerView.Adapter<SentFriendRequestViewHolder> {
    private List<Friendship> friendships;
    private final OnSentFriendRequestClickListener listener;

    public SentFriendRequestAdapter(List<Friendship> friendships, OnSentFriendRequestClickListener listener) {
        this.friendships = friendships;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SentFriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_sent_friend_request, parent, false);
        return new SentFriendRequestViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SentFriendRequestViewHolder holder, int position) {
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

