package vn.edu.ou.zalo.data.sources.fake;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;

import java.util.Random;

public class ChatRoomFakeDataSourceImpl implements IChatRoomDataSource {
    // Random name arrays
    private static final String[] FIRST_NAMES = {"Alice", "John", "Bob", "Lionel", "Jane", "Emma", "Michael", "Sophia"};
    private static final String[] LAST_NAMES = {"Doe", "Smith", "Brown", "Johnson", "Williams", "Jones", "Davis", "Miller"};

    // Random message contents
    private static final String[] MESSAGES = {
            "Hello", "Hi 😊", "Good morning 🌅", "How are you?", "I like this", "See you soon", "Let's meet 🤝",
            "Great job", "Love it ❤️", "Good evening", "How's it going?", "Long time no see 👀",
            "What’s up?", "How’s everything?", "Nice to see you 😊", "Good night 🌙",
            "Take care", "Catch you later 👋", "Nice!", "Well done", "Amazing! 🎉",
            "That’s awesome", "Perfect!", "Thank you 🙏", "You're welcome", "No problem",
            "Anytime 😌", "Sure", "Of course", "I agree 👍", "That makes sense",
            "Got it!", "Sounds good", "Talk to you later", "Can we meet?", "Let's do it 💪",
            "See you tomorrow", "Take it easy", "How's your day?", "I’m good",
            "I’m busy right now", "I’m here 📍", "What’s the plan?", "Let’s catch up 🍻",
            "I’m free tomorrow", "Are you available?", "Can we reschedule?", "I’m running late 🕒",
            "I’ll be there soon", "Almost there 🏃‍♀️", "Just arrived", "I’ll call you ☎️"
    };

    private final Random random = new Random();

    @Inject
    public ChatRoomFakeDataSourceImpl() {
    }

    @Override
    public List<ChatRoom> getChatRooms() {
        List<ChatRoom> chatRooms = new ArrayList<>();

        // Generate a random number of chat rooms (between 10 and 20)
        int numberOfRooms = random.nextInt(11) + 10;

        for (int i = 0; i < numberOfRooms; i++) {
            if (random.nextInt(10) % 2 == 0) {
                // Create 1-1 chat room
                chatRooms.add(generateOneOnOneChatRoom());
            } else {
                // Create group chat room
                chatRooms.add(generateGroupChatRoom());
            }
        }

        // TODO: sorting is not working
        // Sort chat rooms by last message timestamp
        chatRooms.sort(Comparator.comparingLong(chatRoom -> chatRoom.getLastMessage().getTimestamp()));

        return chatRooms;
    }

    private ChatRoom generateOneOnOneChatRoom() {
        // Random user names for 1-1 chat
        String firstName = getRandomFirstName();
        String lastName = getRandomLastName();
        String fullName = firstName + " " + lastName;

        ChatRoom oneOnOneChat = new ChatRoom();
        oneOnOneChat.setType(0); // 0 = 1-1 chat
        oneOnOneChat.setName(fullName);

        // Create member
        ChatRoom.Member member = createMember(fullName, getRandomAvatar());

        // Last message
        ChatRoom.LastMessage lastMessage = new ChatRoom.LastMessage();
        lastMessage.setContent(getRandomMessage());
        lastMessage.setTimestamp(generateRandomTimestamp()); // Set random timestamp
        lastMessage.setSender(member);
        oneOnOneChat.setLastMessage(lastMessage);

        // Members list
        List<ChatRoom.Member> members = new ArrayList<>();
        members.add(createMember(getRandomFirstName() + " " + getRandomLastName(), getRandomAvatar()));
        members.add(member); // Admin or primary user
        oneOnOneChat.setMembers(members);

        return oneOnOneChat;
    }

    private ChatRoom generateGroupChatRoom() {
        ChatRoom groupChat = new ChatRoom();
        groupChat.setType(1); // 1 = Group chat
        groupChat.setName(getRandomGroupName());
        groupChat.setGroupAvatarUrl(getRandomAvatar());

        // Generate random members for group chat
        int numberOfMembers = random.nextInt(3) + 3; // Between 3 and 5 members
        List<ChatRoom.Member> members = new ArrayList<>();
        for (int i = 0; i < numberOfMembers; i++) {
            members.add(createMember(getRandomFirstName() + " " + getRandomLastName(), getRandomAvatar()));
        }

        // Set one member as admin
        members.get(0).setAdmin(true);

        // Last message in the group
        ChatRoom.LastMessage lastMessage = new ChatRoom.LastMessage();
        lastMessage.setContent(getRandomMessage());
        lastMessage.setTimestamp(generateRandomTimestamp()); // Set random timestamp
        lastMessage.setSender(members.get(random.nextInt(members.size()))); // Random sender from the group
        groupChat.setLastMessage(lastMessage);

        groupChat.setMembers(members);
        return groupChat;
    }

    private ChatRoom.Member createMember(String name, String avatar) {
        ChatRoom.Member member = new ChatRoom.Member();
        member.setFullName(name);
        member.setAvatarUrl(avatar);
        member.setMod(random.nextBoolean()); // Randomly set mod status
        return member;
    }

    private String getRandomFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    private String getRandomLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    private String getRandomMessage() {
        return MESSAGES[random.nextInt(MESSAGES.length)];
    }

    private String getRandomGroupName() {
        // Generate a random group name (e.g., "Friends Group", "Work Group")
        String[] groupTypes = {"Friends", "Work", "Family", "Study"};
        return groupTypes[random.nextInt(groupTypes.length)] + " Group";
    }

    private String getRandomAvatar() {
        return "https://avatar.iran.liara.run/public"; // Random avatar generation URL
    }

    private long generateRandomTimestamp() {
        // Generate a random timestamp within the last week
        long now = System.currentTimeMillis();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 1 week in milliseconds
        return now - random.nextInt((int) oneWeekInMillis);
    }
}