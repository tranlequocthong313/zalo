package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.GetPostsUseCase;
import vn.edu.ou.zalo.domain.impl.GetSignedInUserUseCase;
import vn.edu.ou.zalo.domain.impl.GetStoriesUseCase;
import vn.edu.ou.zalo.ui.states.TimelineUiState;

public class TimelineViewModel extends ViewModel {
    private final MutableLiveData<TimelineUiState> uiState =
            new MutableLiveData<>(new TimelineUiState(false, null, new ArrayList<>(), null, new ArrayList<>()));
    private final GetStoriesUseCase getStoriesUseCase;
    private final GetPostsUseCase getPostsUseCase;
    private final GetSignedInUserUseCase getSignedInUser;
    private List<Story> stories = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private User signedInUser;

    @Inject
    public TimelineViewModel(GetStoriesUseCase getStoriesUseCase, GetPostsUseCase getPostsUseCase, GetSignedInUserUseCase getSignedInUser) {
        this.getStoriesUseCase = getStoriesUseCase;
        this.getPostsUseCase = getPostsUseCase;
        this.getSignedInUser = getSignedInUser;
    }

    public LiveData<TimelineUiState> getUiState() {
        return uiState;
    }

    public void fetchData() {
        uiState.setValue(new TimelineUiState(true, null, new ArrayList<>(), null, new ArrayList<>()));

        getStoriesUseCase.execute(null, new IDomainCallback<List<Story>>() {
            @Override
            public void onSuccess(List<Story> data) {
                stories = data;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new TimelineUiState(false, e.getMessage(), stories, signedInUser, posts));
            }
        });
        getPostsUseCase.execute(null, new IDomainCallback<List<Post>>() {
            @Override
            public void onSuccess(List<Post> data) {
                posts = data;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new TimelineUiState(false, e.getMessage(), stories, signedInUser, posts));
            }
        });
        getSignedInUser.execute(new IDomainCallback<User>() {
            @Override
            public void onSuccess(User data) {
                signedInUser = data;
                updateUiState();
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new TimelineUiState(false, e.getMessage(), stories, signedInUser, posts));
            }
        });
    }

    private void updateUiState() {
        uiState.setValue(new TimelineUiState(false, null, stories, signedInUser, posts));
    }
}
