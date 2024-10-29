package vn.edu.ou.zalo.data.models;

import androidx.annotation.Nullable;

import java.util.Set;

public class ChatRoom extends BaseModel {

    public enum Type {SINGLE, GROUP}

    public enum Priority {FOCUSED, OTHER}

    public static class LastMessage {
        private String content;
        private long timestamp;
        private Member sender;

        public static LastMessage fromMessage(Message message) {
            LastMessage lastMessage = new LastMessage();
            lastMessage.setSender(Member.fromUser(message.getSender()));
            lastMessage.setTimestamp(message.getCreatedAt());
            lastMessage.setContent(message.getTextContent());

            return lastMessage;
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

        public Member getSender() {
            return sender;
        }

        public void setSender(Member sender) {
            this.sender = sender;
        }
    }

    public static class Member {
        private User user;
        private boolean isAdmin;
        private boolean isMod;

        public static Member fromUser(User user) {
            Member member = new Member();
            member.setUser(user);
            return member;
        }

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

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != getClass()) return false;
            Member member = (Member) obj;
            return getUser().equals(member.getUser());
        }

        @Override
        public int hashCode() {
            return getUser().hashCode();
        }
    }

    private Type type = Type.SINGLE;
    private String name;
    @Nullable
    private String groupAvatarUrl;
    private LastMessage lastMessage;
    private Set<Member> members;
    private Priority priority = Priority.FOCUSED;

    public Member getOtherMember(User loginUser) {
        ChatRoom.Member[] members = getMembers().toArray(new ChatRoom.Member[0]);
        for (Member member : members) {
            if (!member.getUser().equals(loginUser)) {
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

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }
}
