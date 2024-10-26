package vn.edu.ou.zalo.ui.states;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;

public class TimelineUiState extends BaseUiState {
    private final List<Story> stories;
    private final List<Post> posts;
    private final User loginUser;

    public TimelineUiState(boolean isLoading, String errorMessage, List<Story> stories, User loginUser, List<Post> posts) {
        super(isLoading, errorMessage);
        this.stories = stories;
        this.posts = posts;
        this.loginUser = loginUser;
    }

    public List<Story> getStories() {
        return stories;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
