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

    public FriendRecommendationViewHolder(@NonNull View itemView, OnFriendClickListener listener) {
        super(itemView);

        avatarImageView = itemView.findViewById(R.id.list_item_suggestion_avatar);
        friendSugggestionNameTextView = itemView.findViewById(R.id.list_item_suggestion_name);
        Button addFriendButton = itemView.findViewById(R.id.list_item_suggestion_add_button);

        addFriendButton.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onAddFriendClick((User) itemView.getTag());
            }
        });
    }

    public void bindSuggestion(User suggestion) {
        if (suggestion.getAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(suggestion.getAvatarUrl())
                    .signature(new ObjectKey(suggestion.getId()))
                    .into(avatarImageView);
        }
        friendSugggestionNameTextView.setText(suggestion.getFullName());
    }
}
