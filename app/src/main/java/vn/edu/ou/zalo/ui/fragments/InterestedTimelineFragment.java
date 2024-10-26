package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.adapters.PostAdapter;
import vn.edu.ou.zalo.ui.fragments.adapters.StoryAdapter;
import vn.edu.ou.zalo.ui.states.TimelineUiState;
import vn.edu.ou.zalo.ui.viewmodels.TimelineViewModel;

@AndroidEntryPoint
public class InterestedTimelineFragment extends Fragment {
    @Inject
    TimelineViewModel timelineViewModel;
    private RecyclerView storiesRecyclerView;
    private RecyclerView postsRecyclerView;
    private StoryAdapter storyAdapter;
    private PostAdapter postAdapter;
    private ImageView avatarImageView;

    public static InterestedTimelineFragment newInstance() {
        return new InterestedTimelineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interested_timeline, container, false);

        avatarImageView = view.findViewById(R.id.fragment_timeline_avatar);

        storiesRecyclerView = view.findViewById(R.id.fragment_timeline_stories_recycler_view);
        storiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        postsRecyclerView = view.findViewById(R.id.fragment_timeline_posts_recycler_view);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postsRecyclerView.setFocusable(false);
        postsRecyclerView.setNestedScrollingEnabled(false);

        timelineViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    private void updateUi(TimelineUiState timelineUiState) {
        List<Story> stories = timelineUiState.getStories();
        List<Post> posts = timelineUiState.getPosts();
        User loginUser = timelineUiState.getLoginUser();

        if (loginUser.getAvatarUrl() != null) {
            Glide.with(avatarImageView.getContext())
                    .load(loginUser.getAvatarUrl())
                    .placeholder(R.color.gray)
                    .error(R.color.gray)
                    .into(avatarImageView);
        }

        if (storiesRecyclerView.getAdapter() == null) {
            storyAdapter = new StoryAdapter(stories, loginUser);
            postAdapter = new PostAdapter(posts);
            storiesRecyclerView.setAdapter(storyAdapter);
            postsRecyclerView.setAdapter(postAdapter);
        } else {
            storyAdapter.updateStories(stories);
            postAdapter.updatePosts(posts);
        }
    }
}