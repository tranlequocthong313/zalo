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
import vn.edu.ou.zalo.ui.fragments.listeners.OnFriendClickListener;

public class FriendContactViewHolder extends RecyclerView.ViewHolder {
    private final ImageView avatarImageView;
    private final TextView friendNameTextView;
    private final ImageView onlineStatusImageView;
    private User friend;

    public FriendContactViewHolder(@NonNull View itemView, OnFriendClickListener listener) {
        super(itemView);

        itemView.setOnClickListener(v -> listener.onItemClick(friend));

        avatarImageView = itemView.findViewById(R.id.list_item_friend_avatar);
        friendNameTextView = itemView.findViewById(R.id.list_item_friend_name);
        onlineStatusImageView = itemView.findViewById(R.id.list_item_friend_online_status);
    }

    public void bindFriend(User friend) {
        this.friend = friend;
        if (friend.getAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(friend.getAvatarUrl())
                    .signature(new ObjectKey(friend.getId()))
                    .into(avatarImageView);
        }
        friendNameTextView.setText(friend.getFullName());
        if (!friend.isOnline()) {
            onlineStatusImageView.setVisibility(View.GONE);
        }
    }
}
