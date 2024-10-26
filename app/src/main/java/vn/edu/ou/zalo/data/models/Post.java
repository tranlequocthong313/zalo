package vn.edu.ou.zalo.data.models;

import androidx.annotation.Nullable;

import java.util.List;

public class Post extends BaseModel {
    public enum Visibility {FRIENDS, PRIVATE, SELECTED_FRIEND, EXCEPTED_FRIENDS}

    @Nullable
    private String textContent;
    @Nullable
    private List<String> imageUrls;
    @Nullable
    private String videoUrl;
    @Nullable
    private String linkUrl;
    private int likeCount;
    private int commentCount;
    private Visibility visibility = Visibility.FRIENDS;
    @Nullable
    private List<User> taggedFriends;
    private User author;

    @Nullable
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(@Nullable String textContent) {
        this.textContent = textContent;
    }

    @Nullable
    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(@Nullable List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Nullable
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(@Nullable String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Nullable
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(@Nullable String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Nullable
    public List<User> getTaggedFriends() {
        return taggedFriends;
    }

    public void setTaggedFriends(@Nullable List<User> taggedFriends) {
        this.taggedFriends = taggedFriends;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
