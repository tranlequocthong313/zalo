package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;

public class StoryViewHolder extends RecyclerView.ViewHolder {
    private final ImageView previewImageView;
    private final ImageView avatarImageView;
    private final TextView authorNameTextView;

    public StoryViewHolder(@NonNull View itemView) {
        super(itemView);

        previewImageView = itemView.findViewById(R.id.list_item_story_image);
        avatarImageView = itemView.findViewById(R.id.list_item_story_small_image);
        authorNameTextView = itemView.findViewById(R.id.list_item_story_text);
    }

    public void bindStory(Story story) {
        if (story.getImageUrl() != null) {
            Glide.with(previewImageView.getContext())
                    .load(story.getImageUrl())
                    .signature(new ObjectKey(story.getId()))
                    .into(previewImageView);
        }
        if (story.getAuthor().getAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(story.getAuthor().getAvatarUrl())
                    .signature(new ObjectKey(story.getAuthor().getId()))
                    .into(avatarImageView);
        }
        if (story.getAuthor().getFullName() != null) {
            authorNameTextView.setText(story.getAuthor().getFullName());
        }
    }

    public void bindStory(User loginuser) {
        if (loginuser.getAvatarUrl() != null) {
            Glide.with(previewImageView.getContext())
                    .load(loginuser.getAvatarUrl())
                    .signature(new ObjectKey(loginuser.getId()))
                    .into(previewImageView);
        }
        Drawable drawable = AppCompatResources.getDrawable(avatarImageView.getContext(), R.drawable.ic_pen_solid);
        avatarImageView.setImageDrawable(drawable);
    }
}
