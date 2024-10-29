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
import vn.edu.ou.zalo.ui.activities.ChatActivity;
import vn.edu.ou.zalo.utils.TimeUtils;

public class ChatRoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "ChatRoomViewHolder";

    private final ImageView avatarImageView;
    private final TextView roomNameTextView;
    private final TextView lastMessageTextView;
    private final TextView timeAgoTextView;
    private ChatRoom chatRoom;

    public ChatRoomViewHolder(@NonNull View itemView) {
        super(itemView);

        avatarImageView = itemView.findViewById(R.id.list_item_suggestion_avatar);
        roomNameTextView = itemView.findViewById(R.id.list_item_suggestion_name);
        lastMessageTextView = itemView.findViewById(R.id.list_item_chat_message_last_message);
        timeAgoTextView = itemView.findViewById(R.id.list_item_chat_room_message_time_ago);

        itemView.setOnClickListener(this);
    }

    public void bindChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        if (chatRoom.isGroupChat() && chatRoom.getGroupAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(chatRoom.getGroupAvatarUrl())
                    .signature(new ObjectKey(chatRoom.getId()))
                    .into(avatarImageView);
        } else {
            for (Object obj : chatRoom.getMembers().toArray()) {
                ChatRoom.Member member = (ChatRoom.Member) obj;
                if (member.isAdmin()) {
                    Glide.with(avatarImageView.getContext())
                            .load(member.getUser().getAvatarUrl()) // TODO: for dev purpose
                            .signature(new ObjectKey(member.getUser().getId()))
                            .into(avatarImageView);
                    break;
                }
            }
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

    @Override
    public void onClick(View v) {
        v.getContext()
                .startActivity(ChatActivity.newIntent(v.getContext(), chatRoom.getId()));
    }
}
