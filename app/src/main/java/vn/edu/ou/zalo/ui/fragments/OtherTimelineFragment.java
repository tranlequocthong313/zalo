package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.ui.fragments.adapters.PostAdapter;
import vn.edu.ou.zalo.ui.states.TimelineUiState;
import vn.edu.ou.zalo.ui.viewmodels.TimelineViewModel;

@AndroidEntryPoint
public class OtherTimelineFragment extends Fragment implements IRefreshable {
    @Inject
    TimelineViewModel timelineViewModel;
    private RecyclerView postsRecyclerView;
    private PostAdapter postAdapter;

    public static OtherTimelineFragment newInstance() {
        return new OtherTimelineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_timeline, container, false);

        postsRecyclerView = view.findViewById(R.id.fragment_other_timeline_posts_recycler_view);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postsRecyclerView.setFocusable(false);
        postsRecyclerView.setNestedScrollingEnabled(false);

        timelineViewModel.fetchData();
        timelineViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    private void updateUi(TimelineUiState timelineUiState) {
        if (timelineUiState.isLoading()) {
            return;
        }
        if (timelineUiState.getErrorMessage() != null) {
            return;
        }

        List<Post> posts = timelineUiState.getPosts();

        if (postsRecyclerView.getAdapter() == null) {
            postAdapter = new PostAdapter(posts);
            postsRecyclerView.setAdapter(postAdapter);
        } else {
            postAdapter.updatePosts(posts);
        }
    }

    @Override
    public void refreshContent() {
        timelineViewModel.fetchData();
    }
}