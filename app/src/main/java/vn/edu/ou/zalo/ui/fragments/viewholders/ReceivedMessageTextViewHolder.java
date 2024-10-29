package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.utils.TimeUtils;

public class ReceivedMessageTextViewHolder extends RecyclerView.ViewHolder {
    private final ImageView senderAvatarImageView;
    private final TextView timestampTextView;
    private final TextView contentTextView;
    private final ImageView likeImageView;

    public ReceivedMessageTextViewHolder(@NonNull View itemView) {
        super(itemView);

        senderAvatarImageView = itemView.findViewById(R.id.list_item_received_message_avatar);
        timestampTextView = itemView.findViewById(R.id.list_item_received_message_timestamp);
        contentTextView = itemView.findViewById(R.id.list_item_received_message_text_content);
        likeImageView = itemView.findViewById(R.id.list_item_received_message_like_button);
    }

    public void bindMessage(
            Message message,
            boolean hideAvatar,
            boolean hideTimestamp,
            boolean hideLikeButton) {
        if (!hideAvatar && message.getSender().getAvatarUrl() != null) {
            senderAvatarImageView.setVisibility(View.VISIBLE);
            Glide.with(senderAvatarImageView.getContext())
                    .load(message.getSender().getAvatarUrl())
                    .signature(new ObjectKey(message.getSender().getId()))
                    .into(senderAvatarImageView);
        } else {
            senderAvatarImageView.setVisibility(View.INVISIBLE);
        }
        contentTextView.setText(message.getTextContent());
        if (!hideTimestamp) {
            timestampTextView.setVisibility(View.VISIBLE);
            timestampTextView.setText(TimeUtils.getHourMinute(message.getCreatedAt()));
        } else {
            timestampTextView.setVisibility(View.GONE);
        }
        if (hideLikeButton) {
            likeImageView.setVisibility(View.GONE);
        } else {
            likeImageView.setVisibility(View.VISIBLE);
        }
    }
}
