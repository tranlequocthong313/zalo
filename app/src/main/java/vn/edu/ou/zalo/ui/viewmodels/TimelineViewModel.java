package vn.edu.ou.zalo.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Time;
import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.ui.states.GroupChatRoomUiState;
import vn.edu.ou.zalo.ui.states.TimelineUiState;

public class TimelineViewModel extends ViewModel {
    private final MutableLiveData<TimelineUiState> uiState =
            new MutableLiveData<>(new TimelineUiState(false, null, null, null, null));
    private final IGetListUseCase<Story> getStoriesUseCase;
    private final IGetListUseCase<Post> getPostsUseCase;
    private final IGetDetailUseCase<User> getLoginUserUseCase;

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
            List<Story> stories = getStoriesUseCase.execute();
            List<Post> posts = getPostsUseCase.execute();
            User loginUser = getLoginUserUseCase.execute();
            uiState.setValue(new TimelineUiState(false, null, stories, loginUser, posts));
        } catch (Exception e) {
            uiState.setValue(new TimelineUiState(false, e.getMessage(), null, null, null));
        }
    }
}
