package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.listeners.OnFriendClickListener;

public class FriendRecommendationViewHolder extends RecyclerView.ViewHolder {
    private final ImageView avatarImageView;
    private final TextView friendSugggestionNameTextView;
    private User user;

    public FriendRecommendationViewHolder(@NonNull View itemView, OnFriendClickListener listener) {
        super(itemView);

        avatarImageView = itemView.findViewById(R.id.list_item_suggestion_avatar);
        friendSugggestionNameTextView = itemView.findViewById(R.id.list_item_suggestion_name);
        Button addFriendButton = itemView.findViewById(R.id.list_item_suggestion_add_button);

        addFriendButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddFriendClick(user);
            }
        });
    }

    public void bindSuggestion(User suggestion) {
        user = suggestion;
        if (suggestion.getAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(suggestion.getAvatarUrl())
                    .signature(new ObjectKey(suggestion.getId()))
                    .into(avatarImageView);
        }
        friendSugggestionNameTextView.setText(suggestion.getFullName());
    }
}
