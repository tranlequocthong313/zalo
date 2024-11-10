package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.TimelineUiState;

public class TimelineViewModel extends ViewModel {
    private final MutableLiveData<TimelineUiState> uiState =
            new MutableLiveData<>(new TimelineUiState(false, null, null, null, null));
    private final IGetListUseCase<Story> getStoriesUseCase;
    private final IGetListUseCase<Post> getPostsUseCase;
    private final IGetDetailUseCase<User> getLoginUserUseCase;
    private List<Story> stories = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private User loginUser;

    @Inject
    public TimelineViewModel(IGetListUseCase<Story> getStoriesUseCase, IGetDetailUseCase<User> getLoginUserUseCase, IGetListUseCase<Post> getPostsUseCase) {
        this.getStoriesUseCase = getStoriesUseCase;
        this.getPostsUseCase = getPostsUseCase;
        this.getLoginUserUseCase = getLoginUserUseCase;

        fetchData();
    }

    public LiveData<TimelineUiState> getUiState() {
        return uiState;
    }

    public void fetchData() {
        uiState.setValue(new TimelineUiState(true, null, null, null, null));

        try {
            getStoriesUseCase.execute(null, new IDomainCallback<List<Story>>() {
                @Override
                public void onSuccess(List<Story> data) {
                    stories = data;
                    updateUiState();
                }

                @Override
                public void onFailure(Exception e) {

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

                }
            });
            getLoginUserUseCase.execute(null, new IDomainCallback<User>() {
                @Override
                public void onSuccess(User data) {
                    loginUser = data;
                    updateUiState();
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        } catch (Exception e) {
            uiState.setValue(new TimelineUiState(false, e.getMessage(), null, null, null));
        }
    }

    private void updateUiState() {
        uiState.setValue(new TimelineUiState(false, null, stories, loginUser, posts));
    }

    private User getLoginUser() {
        return Objects.requireNonNull(uiState.getValue()).getLoginUser();
    }

    private List<Post> getPosts() {
        return new ArrayList<>(Objects.requireNonNull(uiState.getValue()).getPosts());
    }

    private List<Story> getStories() {
        return new ArrayList<>(Objects.requireNonNull(uiState.getValue()).getStories());
    }
}
