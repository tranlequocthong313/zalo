package vn.edu.ou.zalo.data.models;

import androidx.annotation.Nullable;

import java.util.List;

public class ChatRoom extends BaseModel {

    public enum Type {SINGLE, GROUP}

    public enum Priority {FOCUSED, OTHER}

    public static class LastMessage {
        private String content;
        private long timestamp;
        private Member sender;

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

        public Member getSender() {
            return sender;
        }

        public void setSender(Member sender) {
            this.sender = sender;
        }
    }

    public static class Member {
        private String fullName;
        private String avatarUrl;
        private boolean isAdmin;
        private boolean isMod;

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
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
    }

    private Type type = Type.SINGLE;
    private String name;
    @Nullable
    private String groupAvatarUrl;
    private LastMessage lastMessage;
    private List<Member> members;
    private Priority priority = Priority.FOCUSED;

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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
