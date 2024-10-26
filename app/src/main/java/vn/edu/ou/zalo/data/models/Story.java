package vn.edu.ou.zalo.data.models;

import androidx.annotation.Nullable;

import java.util.List;

public class Story extends BaseModel {
    public enum Status {VISIBLE, HIDDEN}

    public enum Visibility {FRIEND, SPECIFIC_FRIEND, EXCEPTED_FRIEND}

    @Nullable
    private String imageUrl;
    @Nullable
    private String videoUrl;
    @Nullable
    private String musicUrl;
    private User author;
    private long viewCount;
    private Status status = Status.VISIBLE;
    private Visibility visibility = Visibility.FRIEND;
    @Nullable
    private List<User> specificFriends;
    @Nullable
    private List<User> exceptedFriends;

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(@Nullable String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Nullable
    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(@Nullable String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Nullable
    public List<User> getSpecificFriends() {
        return specificFriends;
    }

    public void setSpecificFriends(@Nullable List<User> specificFriends) {
        this.specificFriends = specificFriends;
    }

    @Nullable
    public List<User> getExceptedFriends() {
        return exceptedFriends;
    }

    public void setExceptedFriends(@Nullable List<User> exceptedFriends) {
        this.exceptedFriends = exceptedFriends;
    }
}
