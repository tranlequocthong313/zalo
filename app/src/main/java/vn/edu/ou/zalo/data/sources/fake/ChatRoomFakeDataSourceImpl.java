package vn.edu.ou.zalo.data.sources.fake;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final List<ChatRoom> chatRooms;
    private final IUserDataSource userDataSource;

    @Inject
    public ChatRoomFakeDataSourceImpl(IUserDataSource userDataSource) {
        this.userDataSource = userDataSource;
        chatRooms = new ArrayList<>();

        // Generate a random number of chat rooms (between 10 and 20)
        int numberOfRooms = random.nextInt(11) + 10;

        for (int i = 0; i < numberOfRooms; i++) {
            ChatRoom chatRoom;
            if (random.nextInt(10) % 2 == 0) {
                // Create 1-1 chat room
                chatRoom = generateOneOnOneChatRoom();
            } else {
                // Create group chat room
                chatRoom = generateGroupChatRoom();
            }
            chatRoom.setPriority(ChatRoom.Priority.values()[random.nextInt(2)]);
            chatRoom.setId(UUID.randomUUID().toString());
            chatRooms.add(chatRoom);
        }

        // Sort chat rooms by last message timestamp
        chatRooms.sort((r1, r2) -> Math.toIntExact(r1.getLastMessage().getTimestamp() - r2.getLastMessage().getTimestamp()));
        Collections.reverse(chatRooms);

        boolean isSorted = true;

        for (int i = 0; i < chatRooms.size() - 1; i++) {
            long currentTimestamp = chatRooms.get(i).getLastMessage().getTimestamp();
            long nextTimestamp = chatRooms.get(i + 1).getLastMessage().getTimestamp();

            if (currentTimestamp < nextTimestamp) {
                isSorted = false;
                break;
            }
        }

        if (isSorted) {
            Log.d("ChatRoomCheck", "Chat rooms are sorted in descending order by timestamp.");
        } else {
            Log.d("ChatRoomCheck", "Chat rooms are NOT sorted in descending order by timestamp.");
        }
    }

    @Override
    public List<ChatRoom> getChatRooms(Map<String, String> query) {
        List<ChatRoom> result = chatRooms.stream()
                .filter(chatRoom -> chatRoom.getMembers().contains(ChatRoom.Member.fromUser(userDataSource.getLoginUser())))
                .collect(Collectors.toList());

        if (query == null) {
            return result;
        }

        if (query.containsKey("priority") && query.get("priority") != null) {
            ChatRoom.Priority priority = ChatRoom.Priority.valueOf(query.get("priority"));
            result = chatRooms.stream().filter(chatRoom -> chatRoom.getPriority() == priority).collect(Collectors.toList());
        }
        if (query.containsKey("type") && query.get("type") != null) {
            ChatRoom.Type type = ChatRoom.Type.valueOf(query.get("type"));
            result = result.stream().filter(chatRoom -> chatRoom.getType() == type).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public ChatRoom getChatRoom(String id) {
        return chatRooms.stream()
                .filter(chatRoom -> Objects.equals(chatRoom.getId(), id))
                .findFirst()
                .orElse(null);
    }

    private ChatRoom generateOneOnOneChatRoom() {
        // Random user names for 1-1 chat
        String firstName = getRandomFirstName();
        String lastName = getRandomLastName();
        String fullName = firstName + " " + lastName;

        ChatRoom oneOnOneChat = new ChatRoom();
        oneOnOneChat.setType(ChatRoom.Type.SINGLE); // 0 = 1-1 chat
        oneOnOneChat.setName(fullName);

        // Create member
        ChatRoom.Member member = ChatRoom.Member.fromUser(userDataSource.getLoginUser());

        // Last message
        ChatRoom.LastMessage lastMessage = new ChatRoom.LastMessage();
        lastMessage.setContent(getRandomMessage());
        lastMessage.setTimestamp(generateRandomTimestamp()); // Set random timestamp
        lastMessage.setSender(member);
        oneOnOneChat.setLastMessage(lastMessage);

        // Members list
        Set<ChatRoom.Member> members = new HashSet<>();
        members.add(createMember(getRandomFirstName() + " " + getRandomLastName(), getRandomAvatar()));
        members.add(member); // Admin or primary user
        oneOnOneChat.setMembers(members);

        return oneOnOneChat;
    }

    private ChatRoom generateGroupChatRoom() {
        ChatRoom groupChat = new ChatRoom();
        groupChat.setType(ChatRoom.Type.GROUP); // 1 = Group chat
        groupChat.setName(getRandomGroupName());
        groupChat.setGroupAvatarUrl(getRandomAvatar());

        // Generate random members for group chat
        int numberOfMembers = random.nextInt(3) + 3; // Between 3 and 5 members
        Set<ChatRoom.Member> members = new HashSet<>();
        members.add(ChatRoom.Member.fromUser(userDataSource.getLoginUser()));
        ChatRoom.LastMessage lastMessage = new ChatRoom.LastMessage();
        lastMessage.setContent(getRandomMessage());
        lastMessage.setTimestamp(generateRandomTimestamp()); // Set random timestamp
        for (int i = 0; i < numberOfMembers; i++) {
            ChatRoom.Member member = createMember(getRandomFirstName() + " " + getRandomLastName(), getRandomAvatar());
            if (i == 0) {
                member.setAdmin(true);
                lastMessage.setSender(member); // Random sender from the group
            }
            members.add(member);
        }

        // Last message in the group
        groupChat.setLastMessage(lastMessage);

        groupChat.setMembers(members);
        return groupChat;
    }

    private ChatRoom.Member createMember(String name, String avatar) {
        List<User> users = userDataSource.getUsers();
        User user = users.get(random.nextInt(users.size()));
        ChatRoom.Member member = ChatRoom.Member.fromUser(user);
        while (member.getUser().equals(userDataSource.getLoginUser())) {
            user = users.get(random.nextInt(users.size()));
            member = ChatRoom.Member.fromUser(user);
            member.setMod(random.nextBoolean()); // Randomly set mod status
        }
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