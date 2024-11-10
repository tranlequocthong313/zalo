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
import vn.edu.ou.zalo.ui.fragments.viewholders.FriendSuggestionViewHolder;

public class FriendSuggestionAdapter extends RecyclerView.Adapter<FriendSuggestionViewHolder> {
    private List<User> friendSuggestions;

    public FriendSuggestionAdapter(List<User> friendSuggestions) {
        this.friendSuggestions = friendSuggestions;
    }

    @NonNull
    @Override
    public FriendSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friend_suggestion, parent, false);

        return new FriendSuggestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendSuggestionViewHolder holder, int position) {
        User friendSuggestion = friendSuggestions.get(position);
        holder.bindSuggestion(friendSuggestion);
    }

    @Override
    public int getItemCount() {
        return friendSuggestions.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateFriendSuggestions(List<User> newSuggestions) {
        if (newSuggestions != null) {
            this.friendSuggestions = newSuggestions;
            notifyDataSetChanged();
        }
    }
}
