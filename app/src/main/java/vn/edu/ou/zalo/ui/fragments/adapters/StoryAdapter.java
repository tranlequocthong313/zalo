package vn.edu.ou.zalo.ui.fragments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.viewholders.StoryViewHolder;

public class StoryAdapter extends RecyclerView.Adapter<StoryViewHolder> {
    private static final int VIEW_TYPE_CREATE_NEW = 0;
    private static final int VIEW_TYPE_STORY = 1;

    private List<Story> stories;
    private final User loginuser;

    public StoryAdapter(List<Story> stories, User loginUser) {
        this.stories = stories;
        this.loginuser = loginUser;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_CREATE_NEW) {
            holder.bindStory(loginuser);
        } else {
            holder.bindStory(stories.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_CREATE_NEW;
        }
        return VIEW_TYPE_STORY;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateStories(List<Story> stories) {
        if (stories == null) {
            return;
        }
        this.stories = stories;
        notifyDataSetChanged();
    }
}
