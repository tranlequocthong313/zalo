package vn.edu.ou.zalo.data.models;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Set;

@IgnoreExtraProperties
public class ChatRoom extends BaseModel {

    public enum Type {SINGLE, GROUP}

    public enum Priority {FOCUSED, OTHER}

    @IgnoreExtraProperties
    public static class LastMessage {
        public enum Type {TEXT, IMAGE, VIDEO, FILE}

        private String content;
        private long timestamp;
        private Member sender;
        private String senderId;
        private LastMessage.Type type = Type.TEXT;

        public static LastMessage fromMessage(Message message) {
            LastMessage lastMessage = new LastMessage();
            if (message.getImageUrls() != null && !message.getImageUrls().isEmpty()) {
                lastMessage.setType(Type.IMAGE);
            } else if (message.getVideoUrls() != null && !message.getVideoUrls().isEmpty()) {
                lastMessage.setType(Type.VIDEO);
            } else if (message.getFileUrl() != null && !message.getFileUrl().isEmpty()) {
                lastMessage.setType(Type.FILE);
            } else {
                lastMessage.setType(Type.TEXT);
                lastMessage.setContent(message.getTextContent());
            }
            lastMessage.setSender(Member.fromUser(message.getSender()));
            lastMessage.setTimestamp(message.getCreatedAt());
            lastMessage.setSenderId(lastMessage.getSender().getId());

            return lastMessage;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        @Exclude
        public Member getSender() {
            return sender;
        }

        public void setSender(Member sender) {
            this.sender = sender;
        }
    }

    @IgnoreExtraProperties
    public static class Member {
        private User user;
        private String id;
        private boolean isAdmin;
        private boolean isMod;

        public static Member fromUser(User user) {
            return fromUser(user, false, false);
        }

        public static Member fromUser(User user, boolean isAdmin, boolean isMod) {
            Member member = new Member();
            member.setUser(user);
            member.setId(user.getId());
            member.setAdmin(isAdmin);
            member.setMod(isMod);
            return member;
        }

        @Exclude
        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }

        public boolean isMod() {
            return isMod;
        }

        public void setMod(boolean mod) {
            isMod = mod;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    private Type type = Type.SINGLE;
    private String name;
    @Nullable
    private String groupAvatarUrl;
    private LastMessage lastMessage;
    private Set<Member> members;
    private Priority priority = Priority.FOCUSED;

    public Member getOtherMember(User signedInUser) {
        ChatRoom.Member[] members = getMembers().toArray(new ChatRoom.Member[0]);
        for (Member member : members) {
            if (!member.getId().equals(signedInUser.getId())) {
                return member;
            }
        }
        return null;
    }

    public boolean isGroupChat() {
        return Type.GROUP == type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getGroupAvatarUrl() {
        return groupAvatarUrl;
    }

    public void setGroupAvatarUrl(@Nullable String groupAvatarUrl) {
        this.groupAvatarUrl = groupAvatarUrl;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Exclude
    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }
}
