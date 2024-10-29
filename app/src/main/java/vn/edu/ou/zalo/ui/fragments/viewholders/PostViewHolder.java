package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.utils.TimeUtils;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private final ImageView authorAvatarImageView;
    private final TextView authorNameTextView;
    private final TextView postTimestampTextView;
    private final ImageView contentImageView;
    private final TextView contentTextView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        authorAvatarImageView = itemView.findViewById(R.id.list_item_post_avatar);
        authorNameTextView = itemView.findViewById(R.id.list_item_post_author_name);
        contentTextView = itemView.findViewById(R.id.list_item_post_post_text);
        contentImageView = itemView.findViewById(R.id.list_item_post_image_container);
        postTimestampTextView = itemView.findViewById(R.id.list_item_post_timestamp);
    }

    public void bindPost(Post post) {
        contentTextView.setText(post.getTextContent());
        if (post.getAuthor().getAvatarUrl() != null) {
            Glide.with(authorAvatarImageView.getContext())
                    .load(post.getAuthor().getAvatarUrl())
                    .signature(new ObjectKey(post.getAuthor().getId()))
                    .into(authorAvatarImageView);
        }
        if (post.getImageUrls() != null && !post.getImageUrls().isEmpty()) {
            Glide.with(contentImageView.getContext())
                    .load(post.getImageUrls().get(0)) // TODO: for dev purpose
                    .signature(new ObjectKey(post.getId()))
                    .into(contentImageView);
        }
        authorNameTextView.setText(post.getAuthor().getFullName());
        postTimestampTextView.setText(TimeUtils.getDetailedTimeAgo(post.getCreatedAt()));
    }
}
