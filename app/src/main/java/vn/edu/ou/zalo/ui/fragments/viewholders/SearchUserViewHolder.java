package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import java.util.Map;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.listeners.OnFriendClickListener;

public class SearchUserViewHolder extends RecyclerView.ViewHolder {
    private final ImageView avatarImageView;
    private final TextView nameTextView;
    private final TextView phoneNumberTextView;
    private final Button addFriendButton;

    public SearchUserViewHolder(@NonNull View itemView, OnFriendClickListener listener) {
        super(itemView);

        itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(getUser());
            }
        });

        avatarImageView = itemView.findViewById(R.id.list_item_search_result_user_avatar);
        nameTextView = itemView.findViewById(R.id.list_item_search_result_user_name);
        phoneNumberTextView = itemView.findViewById(R.id.list_item_search_result_user_phone_number);
        addFriendButton = itemView.findViewById(R.id.list_item_search_result_user_add_button);

        addFriendButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddFriendClick(getUser());
            }
        });
    }

    private User getUser() {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            return (User) itemView.getTag();
        }
        return null;
    }

    public void bindUser(User user, User signedInUser, Map<String, Friendship.Status> statusMap) {
        if (user.getAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(user.getAvatarUrl())
                    .signature(new ObjectKey(user.getId()))
                    .into(avatarImageView);
        }
        nameTextView.setText(user.getFullName());
        phoneNumberTextView.setText(user.getPhoneNumber());
        itemView.setTag(user);

        if (statusMap != null && statusMap.containsKey(user.getId()) && statusMap.get(user.getId()) != null || signedInUser != null && user.getId().equals(signedInUser.getId())) {
            addFriendButton.setVisibility(View.GONE);
        } else {
            addFriendButton.setVisibility(View.VISIBLE);
        }
    }
}
