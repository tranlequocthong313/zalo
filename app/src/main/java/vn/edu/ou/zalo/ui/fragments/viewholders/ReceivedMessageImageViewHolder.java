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

public class ReceivedMessageImageViewHolder extends RecyclerView.ViewHolder {
    private final ImageView senderAvatarImageView;
    private final TextView timestampTextView;
    private final ImageView contentImageView;
    private final ImageView likeImageView;

    public ReceivedMessageImageViewHolder(@NonNull View itemView) {
        super(itemView);

        senderAvatarImageView = itemView.findViewById(R.id.list_item_received_message_avatar);
        timestampTextView = itemView.findViewById(R.id.list_item_received_message_timestamp);
        contentImageView = itemView.findViewById(R.id.list_item_received_message_image_content);
        likeImageView = itemView.findViewById(R.id.list_item_received_message_like_button);
    }

    public void bindMessage(
            Message message,
            boolean hideTimestamp) {
        if (message.getSender().getAvatarUrl() != null) {
            senderAvatarImageView.setVisibility(View.VISIBLE);
            Glide.with(senderAvatarImageView.getContext())
                    .load(message.getSender().getAvatarUrl())
                    .signature(new ObjectKey(message.getSender().getId()))
                    .into(senderAvatarImageView);
        }

        if (message.getImageUrls().length > 0) {
            Glide.with(contentImageView.getContext())
                    .load(message.getImageUrls()[0])
                    .signature(new ObjectKey(message.getId()))
                    .into(contentImageView);
        }

        if (!hideTimestamp) {
            timestampTextView.setVisibility(View.VISIBLE);
            timestampTextView.setText(TimeUtils.getHourMinute(message.getCreatedAt()));
        } else {
            timestampTextView.setVisibility(View.GONE);
        }
    }
}
