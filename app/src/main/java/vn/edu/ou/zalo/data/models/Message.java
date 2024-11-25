package vn.edu.ou.zalo.data.models;

import android.net.Uri;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Message extends BaseModel {
    public enum Type {TEXT, IMAGE, VIDEO, FILE}

    private String senderId;
    private String chatRoomId;
    @Nullable
    private String textContent;
    @Nullable
    private List<String> imageUrls;
    @Nullable
    private List<String> videoUrls;
    @Nullable
    private String fileUrl;
    @Nullable
    private Message parentMessage;
    private Type type;
    private int likeCount;
    private ChatRoom chatRoom;
    private User sender;
    private List<Uri> fileUris;

    @Exclude
    public List<Uri> getFileUris() {
        return fileUris;
    }

    public void setFileUris(List<Uri> fileUris) {
        this.fileUris = fileUris;
    }

    @Exclude
    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Exclude
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

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

    @Nullable
    public List<String> getVideoUrls() {
        return videoUrls;
    }

    public void setImageUrls(@Nullable List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setVideoUrls(@Nullable List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    @Nullable
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(@Nullable String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Nullable
    public Message getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(@Nullable Message parentMessage) {
        this.parentMessage = parentMessage;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
