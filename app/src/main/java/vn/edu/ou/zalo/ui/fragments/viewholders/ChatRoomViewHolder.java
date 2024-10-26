package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.utils.TimeUtils;

public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ChatRoomViewHolder";

    private final ImageView avatarImageView;
    private final TextView roomNameTextView;
    private final TextView lastMessageTextView;
    private final TextView timeAgoTextView;

    public ChatRoomViewHolder(@NonNull View itemView) {
        super(itemView);

        avatarImageView = itemView.findViewById(R.id.list_item_suggestion_avatar);
        roomNameTextView = itemView.findViewById(R.id.list_item_suggestion_name);
        lastMessageTextView = itemView.findViewById(R.id.list_item_chat_message_last_message);
        timeAgoTextView = itemView.findViewById(R.id.list_item_chat_room_message_time_ago);
    }

    public void bindChatRoom(ChatRoom chatRoom) {
        if (chatRoom.isGroupChat() && chatRoom.getGroupAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(chatRoom.getGroupAvatarUrl())
                    .placeholder(R.color.gray)
                    .error(R.color.gray)
                    .signature(new ObjectKey(System.currentTimeMillis()))
                    .into(avatarImageView);
        } else {
            Glide.with(avatarImageView.getContext())
                    .load(chatRoom.getMembers().get(0).getAvatarUrl()) // TODO: for dev purpose
                    .placeholder(R.color.gray)
                    .error(R.color.gray)
                    .signature(new ObjectKey(System.currentTimeMillis()))
                    .into(avatarImageView);
        }

        roomNameTextView.setText(chatRoom.getName());

        ChatRoom.LastMessage lastMessage = chatRoom.getLastMessage();
        if (lastMessage == null) {
            return;
        }
        lastMessageTextView.setText(lastMessage.getContent());

        long timestamp = lastMessage.getTimestamp();
        String timeAgo = TimeUtils.getShortTimeAgo(timestamp);
        timeAgoTextView.setText(timeAgo);
    }
}
