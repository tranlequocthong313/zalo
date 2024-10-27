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
import vn.edu.ou.zalo.data.models.ChatRoom;

public class UnimportantChatRoomSuggestionViewHolder extends RecyclerView.ViewHolder {
    private final ImageView avatarImageView;
    private final TextView chatNameTextView;

    public UnimportantChatRoomSuggestionViewHolder(@NonNull View itemView) {
        super(itemView);

        avatarImageView = itemView.findViewById(R.id.list_item_suggestion_avatar);
        chatNameTextView = itemView.findViewById(R.id.list_item_suggestion_name);
        Button addButton = itemView.findViewById(R.id.list_item_suggestion_add_button);
        addButton.setText(R.string.add);
    }

    public void bindSuggestion(ChatRoom chatRoom) {
        if (chatRoom.isGroupChat() && chatRoom.getGroupAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(chatRoom.getGroupAvatarUrl())
                    .signature(new ObjectKey(System.currentTimeMillis()))
                    .into(avatarImageView);
        } else {
            Glide.with(avatarImageView.getContext())
                    .load(chatRoom.getMembers().get(0).getAvatarUrl()) // TODO: for dev purpose
                    .signature(new ObjectKey(System.currentTimeMillis()))
                    .into(avatarImageView);
        }
        chatNameTextView.setText(chatRoom.getName());
    }
}
