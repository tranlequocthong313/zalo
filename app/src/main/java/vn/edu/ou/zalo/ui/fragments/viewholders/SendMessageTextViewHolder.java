package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.utils.TimeUtils;

public class SendMessageTextViewHolder extends RecyclerView.ViewHolder {
    private final TextView timestampTextView;
    private final TextView contentTextView;
    private final ImageView likeImageView;

    public SendMessageTextViewHolder(@NonNull View itemView) {
        super(itemView);

        timestampTextView = itemView.findViewById(R.id.list_item_send_message_timestamp);
        contentTextView = itemView.findViewById(R.id.list_item_send_message_text_content);
        likeImageView = itemView.findViewById(R.id.list_item_send_message_like_button);
    }

    public void bindMessage(
            Message message,
            boolean hideTimestamp,
            boolean hideLikeButton) {
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
