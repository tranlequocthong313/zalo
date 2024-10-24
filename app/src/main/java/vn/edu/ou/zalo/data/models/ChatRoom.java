package vn.edu.ou.zalo.data.models;

import androidx.annotation.Nullable;

import java.util.List;

public class ChatRoom extends BaseModel {
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

    private int type = 0; // NOTE: 0 = 1-1 chat, 1 = group chat
    private String name;
    @Nullable
    private String groupAvatarUrl;
    private LastMessage lastMessage;
    private List<Member> members;
    private int priority = 0; // NOTE: 0 = Focused, 1 = Other

    public boolean isGroupChat() {
        return type == 1;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
