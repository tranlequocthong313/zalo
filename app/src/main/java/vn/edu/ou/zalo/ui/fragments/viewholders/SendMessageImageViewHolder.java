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

public class SendMessageImageViewHolder extends RecyclerView.ViewHolder {
    private final TextView timestampTextView;
    private final ImageView contentImageView;

    public SendMessageImageViewHolder(@NonNull View itemView) {
        super(itemView);

        timestampTextView = itemView.findViewById(R.id.list_item_send_message_timestamp);
        contentImageView = itemView.findViewById(R.id.list_item_send_message_image);
    }

    public void bindMessage(
            Message message,
            boolean hideTimestamp) {
        if (message.getImageUrls() != null && !message.getImageUrls().isEmpty()) {
            Glide.with(contentImageView.getContext())
                    .load(message.getImageUrls().get(0))
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
