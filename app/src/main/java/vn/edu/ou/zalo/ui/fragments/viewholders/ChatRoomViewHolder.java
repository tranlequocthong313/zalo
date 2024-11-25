package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.listeners.OnChatRoomItemClickListener;
import vn.edu.ou.zalo.utils.TimeUtils;

public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ChatRoomViewHolder";

    private final ImageView avatarImageView;
    private final TextView roomNameTextView;
    private final TextView lastMessageTextView;
    private final TextView timeAgoTextView;
    private final User signedInUser;
    private final Context context;
    private ChatRoom chatRoom;

    public ChatRoomViewHolder(@NonNull View itemView, User signedInUser, OnChatRoomItemClickListener listener) {
        super(itemView);

        context = itemView.getContext();

        avatarImageView = itemView.findViewById(R.id.list_item_suggestion_avatar);
        roomNameTextView = itemView.findViewById(R.id.list_item_suggestion_name);
        lastMessageTextView = itemView.findViewById(R.id.list_item_chat_message_last_message);
        timeAgoTextView = itemView.findViewById(R.id.list_item_chat_room_message_time_ago);
        this.signedInUser = signedInUser;

        if (listener != null) {
            itemView.setOnClickListener(v -> listener.onItemClick(chatRoom));
        }
    }

    public void bindChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        if (chatRoom.isGroupChat() && chatRoom.getGroupAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(chatRoom.getGroupAvatarUrl())
                    .signature(new ObjectKey(chatRoom.getId()))
                    .into(avatarImageView);
        }

        if (chatRoom.isGroupChat()) {
            roomNameTextView.setText(chatRoom.getName());
        } else {
            ChatRoom.Member member = chatRoom.getOtherMember(signedInUser);
            if (member != null) {
                roomNameTextView.setText(member.getUser().getFullName());
                Glide.with(avatarImageView.getContext())
                        .load(member.getUser().getAvatarUrl())
                        .signature(new ObjectKey(member.getUser().getId()))
                        .into(avatarImageView);
            }
        }

        ChatRoom.LastMessage lastMessage = chatRoom.getLastMessage();
        if (lastMessage == null) {
            return;
        }
        if (lastMessage.getType() == ChatRoom.LastMessage.Type.TEXT) {
            lastMessageTextView.setText(lastMessage.getContent());
        } else if (lastMessage.getType() == ChatRoom.LastMessage.Type.IMAGE) {
            String text = "[" + ContextCompat.getString(context, R.string.photo) + "]";
            lastMessageTextView.setText(text);
        }

        long timestamp = lastMessage.getTimestamp();
        String timeAgo = TimeUtils.getShortTimeAgo(timestamp);
        timeAgoTextView.setText(timeAgo);
    }
}
