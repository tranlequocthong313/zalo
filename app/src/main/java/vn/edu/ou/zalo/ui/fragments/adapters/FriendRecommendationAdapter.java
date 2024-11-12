package vn.edu.ou.zalo.ui.fragments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.listeners.OnAddFriendClickListener;
import vn.edu.ou.zalo.ui.fragments.viewholders.FriendRecommendationViewHolder;

public class FriendRecommendationAdapter extends RecyclerView.Adapter<FriendRecommendationViewHolder> {
    private List<User> friendRecommendations;
    private final OnAddFriendClickListener listener;

    public FriendRecommendationAdapter(List<User> friendRecommendations, OnAddFriendClickListener listener) {
        this.friendRecommendations = friendRecommendations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendRecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friend_recommendation, parent, false);

        return new FriendRecommendationViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRecommendationViewHolder holder, int position) {
        User friendRecommendation = friendRecommendations.get(position);
        holder.bindSuggestion(friendRecommendation);
    }

    @Override
    public int getItemCount() {
        return friendRecommendations.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateFriendSuggestions(List<User> newSuggestions) {
        if (newSuggestions != null) {
            this.friendRecommendations = newSuggestions;
            notifyDataSetChanged();
        }
    }
}
