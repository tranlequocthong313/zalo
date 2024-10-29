package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;

public class FriendSuggestionViewHolder extends RecyclerView.ViewHolder {
    private final ImageView avatarImageView;
    private final TextView friendSugggestionNameTextView;

    public FriendSuggestionViewHolder(@NonNull View itemView) {
        super(itemView);

        avatarImageView = itemView.findViewById(R.id.list_item_suggestion_avatar);
        friendSugggestionNameTextView = itemView.findViewById(R.id.list_item_suggestion_name);
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
