package vn.edu.ou.zalo.data.models;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message extends BaseModel {
    public enum Type {TEXT, IMAGE, VIDEO, FILE}

    private String senderId;
    private String chatRoomId;
    @Nullable
    private String textContent;
    @Nullable
    private String[] imageUrls;
    @Nullable
    private String[] videoUrls;
    @Nullable
    private String fileUrl;
    @Nullable
    private Message parentMessage;
    private Type type;
    private int likeCount;
    private ChatRoom chatRoom;
    private User sender;


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

    public String[] getImageUrls() {
        return imageUrls;
    }

    @Nullable
    public String[] getVideoUrls() {
        return videoUrls;
    }

    public void setImageUrls(@Nullable String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setVideoUrls(@Nullable String[] videoUrls) {
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
