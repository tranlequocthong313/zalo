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
import vn.edu.ou.zalo.ui.fragments.listeners.OnAddFriendClickListener;

public class SearchUserViewHolder extends RecyclerView.ViewHolder {
    private final ImageView avatarImageView;
    private final TextView nameTextView;
    private final TextView phoneNumberTextView;

    public SearchUserViewHolder(@NonNull View itemView, OnAddFriendClickListener listener) {
        super(itemView);

        avatarImageView = itemView.findViewById(R.id.list_item_search_result_user_avatar);
        nameTextView = itemView.findViewById(R.id.list_item_search_result_user_name);
        phoneNumberTextView = itemView.findViewById(R.id.list_item_search_result_user_phone_number);

        Button addFriendButton = itemView.findViewById(R.id.list_item_search_result_user_add_button);
        addFriendButton.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                User user = (User) itemView.getTag();
                if (user != null) {
                    listener.onAddFriendClick(user);
                }
            }
        });
    }

    public void bindUser(User user) {
        if (user.getAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(user.getAvatarUrl())
                    .signature(new ObjectKey(user.getId()))
                    .into(avatarImageView);
        }
        nameTextView.setText(user.getFullName());
        phoneNumberTextView.setText(user.getPhoneNumber());
        itemView.setTag(user);
    }
}
