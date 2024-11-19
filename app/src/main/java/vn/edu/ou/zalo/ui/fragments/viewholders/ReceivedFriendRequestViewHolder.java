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
import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.listeners.OnReceivedFriendRequestClickListener;
import vn.edu.ou.zalo.utils.TimeUtils;

public class ReceivedFriendRequestViewHolder extends RecyclerView.ViewHolder {

    private final ImageView avatar;
    private final TextView name;
    private final TextView timeAgo;
    private Friendship friendship;

    public ReceivedFriendRequestViewHolder(@NonNull View itemView, OnReceivedFriendRequestClickListener listener) {
        super(itemView);

        avatar = itemView.findViewById(R.id.list_item_received_friend_request_avatar);
        name = itemView.findViewById(R.id.list_item_received_friend_request_name);
        timeAgo = itemView.findViewById(R.id.list_item_received_friend_request_time_ago);
        Button refuseButton = itemView.findViewById(R.id.list_item_received_friend_request_refuse_button);
        Button acceptButton = itemView.findViewById(R.id.list_item_received_friend_request_accept_button);

        if (listener != null) {
            refuseButton.setOnClickListener(v -> listener.onRefuse(friendship));
            acceptButton.setOnClickListener(v -> listener.onAccept(friendship));
        }
    }

    public void bindFriendRequest(Friendship friendship) {
        this.friendship = friendship;
        User user = friendship.getSender();

        if (user.getAvatarUrl() != null) {
            Glide.with(avatar)
                    .load(user.getAvatarUrl())
                    .signature(new ObjectKey(user.getId()))
                    .into(avatar);
        }
        name.setText(user.getFullName());
        timeAgo.setText(TimeUtils.getDetailedTimeAgo(friendship.getCreatedAt()));
    }
}
