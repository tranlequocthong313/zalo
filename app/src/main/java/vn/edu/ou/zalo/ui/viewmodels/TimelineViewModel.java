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
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.GetSignedInUserUseCase;
import vn.edu.ou.zalo.ui.states.TimelineUiState;

public class TimelineViewModel extends ViewModel {
    private final MutableLiveData<TimelineUiState> uiState =
            new MutableLiveData<>(new TimelineUiState(false, null, new ArrayList<>(), null, new ArrayList<>()));
    private final IGetListUseCase<Story> getStoriesUseCase;
    private final IGetListUseCase<Post> getPostsUseCase;
    private final GetSignedInUserUseCase getSignedInUser;
    private List<Story> stories = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private User loginUser;

    @Inject
    public TimelineViewModel(IGetListUseCase<Story> getStoriesUseCase, IGetListUseCase<Post> getPostsUseCase, GetSignedInUserUseCase getSignedInUser) {
        this.getStoriesUseCase = getStoriesUseCase;
        this.getPostsUseCase = getPostsUseCase;
        this.getSignedInUser = getSignedInUser;

        fetchData();
    }

    public LiveData<TimelineUiState> getUiState() {
        return uiState;
    }

    public void fetchData() {
        uiState.setValue(new TimelineUiState(true, null, new ArrayList<>(), null, new ArrayList<>()));

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
            getSignedInUser.execute(new IDomainCallback<User>() {
                @Override
                public void onSuccess(User data) {
                    loginUser = data;
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
            updateUiState();
        } catch (Exception e) {
            uiState.setValue(new TimelineUiState(false, e.getMessage(), new ArrayList<>(), null, new ArrayList<>()));
        }
    }

    private void updateUiState() {
        uiState.setValue(new TimelineUiState(false, null, stories, loginUser, posts));
    }
}
