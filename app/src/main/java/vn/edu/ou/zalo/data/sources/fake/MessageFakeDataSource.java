package vn.edu.ou.zalo.data.sources.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.data.sources.IMessageDataSource;

public class MessageFakeDataSource implements IMessageDataSource {

    private final IChatRoomDataSource chatRoomDataSource;
    private User currentUser;
    private List<Message> messages;

    @Inject
    public MessageFakeDataSource(IAuthDataSource authDataSource, IChatRoomDataSource chatRoomDataSource) {
        this.chatRoomDataSource = chatRoomDataSource;
        authDataSource.getSignedInUser(new IRepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                currentUser = data;
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        initMessages();
    }

    @Override
    public void listenMessages(String chatRoomId, IRepositoryCallback<List<Message>> cb) {

    }

    @Override
    public void getMessages(String chatRoomId, IRepositoryCallback<List<Message>> dataSourceCallback) {
        List<Message> results = messages;
        if (chatRoomId != null) {
            results = messages.stream()
                    .filter(message -> message.getChatRoom().getId().equals(chatRoomId))
                    .collect(Collectors.toList());
        }
        dataSourceCallback.onSuccess(results);
    }

    @Override
    public void createMessage(Message message, IRepositoryCallback<Message> cb) {

    }

    private void initMessages() {
        messages = new ArrayList<>();
        chatRoomDataSource.getChatRooms(null, new IRepositoryCallback<List<ChatRoom>>() {
            @Override
            public void onSuccess(List<ChatRoom> chatRooms) {
                Random random = new Random();

                for (ChatRoom room : chatRooms) {
                    int num = random.nextInt(10) + 10; // 10 to 19 messages per room
                    for (int i = 0; i < num; i++) {
                        Message message = new Message();
                        message.setId(UUID.randomUUID().toString());
                        message.setCreatedAt(System.currentTimeMillis() - random.nextInt(100000));
                        message.setUpdatedAt(message.getCreatedAt());

                        // Set sender
                        if (room.isGroupChat()) {
                            ChatRoom.Member[] members = room.getMembers().toArray(new ChatRoom.Member[0]);
                            message.setSender(i % 3 == 0 ? currentUser : members[random.nextInt(members.length)].getUser());
                        } else {
                            ChatRoom.Member[] members = room.getMembers().toArray(new ChatRoom.Member[0]);
                            message.setSender(members[random.nextInt(2)].getUser());
                        }

                        message.setLikeCount(random.nextInt(50));
                        message.setChatRoom(room);
                        room.setLastMessage(ChatRoom.LastMessage.fromMessage(message));

                        // Set message type and content
                        Message.Type type = Message.Type.values()[random.nextInt(2)];
                        message.setType(type);

                        if (type == Message.Type.TEXT) {
                            message.setTextContent(generateRandomTextContent());
                        } else if (type == Message.Type.IMAGE) {
                            // Generate image URLs with varying sizes from very small to very large
                            int width = random.nextInt(1200) + 50;  // Width between 50 and 1250
                            int height = random.nextInt(1200) + 50; // Height between 50 and 1250
                            message.setImageUrls(List.of("https://picsum.photos/" + width + "/" + height));
                        }

                        messages.add(message);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private String generateRandomTextContent() {
        String[] shortTexts = {
                "Just saw the most beautiful sunset! 🌅",
                "Feeling pumped for a new project! 💻",
                "Today was all about self-care. 💆‍♀️",
                "Excited for movie night with friends! 🎬",
                "Trying out a new hobby this week! 🎨",
                "Spending the day in my favorite cafe! ☕",
                "Looking forward to the holiday season! 🎄",
                "Feeling energized after a great workout! 🏃‍♂️",
                "Just finished reading a fantastic book! 📖",
                "Today is a good day for some adventure! 🌍",
                "Life is full of surprises! ✨",
                "Enjoying some quality time with family! ❤️",
                "Just got a promotion at work! 🎉",
                "Taking a moment to appreciate the small things. 🌼",
                "Ready to take on new challenges! 💪",
                "Celebrating milestones, big and small! 🎊",
                "Today feels like a fresh start! 🌈",
                "Finding joy in the little moments. 😊",
                "Exploring the city like a tourist! 🏙️",
                "Grateful for my supportive friends. 🙌",
                "It's a perfect day for a picnic! 🍉",
                "The little things in life matter the most! 🌸",
                "Cherishing the moments that bring me joy! 🥰",
                "Feeling inspired by new beginnings! 🌟",
                "Today, I choose to be happy! 😃",
                "Caught up with an old friend today! 🤗",
                "Savoring every moment of this weekend! 🎈",
                "Wishing for peace and happiness for everyone! 🌼",
                "Reflecting on the journey so far. 🌌",
                "Here’s to new adventures ahead! 🚀"
        };

        String[] mediumTexts = {
                "Tried a new recipe for dinner, and it was a huge hit! Cooking brings so much joy to my life. 🍽️",
                "Spent the afternoon volunteering, and it felt amazing to give back to the community. 💖",
                "Just finished a great podcast that made me rethink my perspective on life. 🎧",
                "Went on a spontaneous day trip and explored some hidden gems in the city. 🗺️",
                "The energy at the concert last night was incredible! Nothing beats live music! 🎶",
                "Took some time to meditate today, and it really helped clear my mind. 🧘‍♀️",
                "Had a deep conversation with a friend today. It's so important to connect on a deeper level. 🤝",
                "Went hiking this weekend, and the views were breathtaking! Nature truly heals the soul. 🌲",
                "Just signed up for a dance class! Can't wait to learn something new and have fun! 💃",
                "Feeling grateful for the opportunities life has presented me lately. 🌟",
                "Had a cozy movie marathon with hot cocoa and snacks! Just what I needed! 🍫",
                "The sun was shining bright today, and it made everything feel more vibrant. ☀️",
                "Spent some time reflecting on my goals and dreams. It's important to stay focused! 🎯",
                "I’m learning how to play the guitar, and it’s both challenging and rewarding! 🎸",
                "Took a break from technology and spent the day outside. It felt refreshing! 🌞",
                "Had a fantastic lunch with a friend, catching up and enjoying great food! 🍴",
                "I started a new book series, and I'm hooked! Can't put it down! 📚",
                "Just finished a workout challenge, and I'm feeling strong and accomplished! 🏋️‍♀️",
                "Today was all about relaxation and self-care. I really needed that! 🛁",
                "Explored a new part of town today and found some hidden treasures! 🏙️",
                "Life is a beautiful journey, and I'm excited for what's ahead! 🚀"
        };

        String[] longTexts = {
                "Today was an incredible day! I visited a local museum and learned so much about history. "
                        + "The exhibitions were fascinating, and I felt like I was transported back in time! 🏛️",

                "I've been focusing on my personal development lately. Reading self-help books and attending workshops has been eye-opening. "
                        + "I feel more empowered and ready to tackle any challenge that comes my way! 📈",

                "This weekend, I went on a road trip with some friends. We explored beautiful landscapes, laughed until we cried, and created unforgettable memories. "
                        + "There's nothing like a spontaneous adventure! 🚗",

                "I've started a gratitude journal, and it’s been a transformative experience. Each day, I write down things I'm thankful for, and it truly shifts my mindset to positivity. 📝",

                "After months of hard work, I finally completed a major project at work! The feeling of accomplishment is surreal, and I'm proud of what I've achieved. 🎉",

                "Spent the day learning about sustainability and how to reduce my carbon footprint. It feels good to contribute to a healthier planet! 🌍",

                "I went to a cooking class today, and it was so much fun! I learned how to make authentic Italian pasta from scratch. Can’t wait to try it at home! 🍝",

                "Reflecting on the past year, I realize how much I've grown. Life is a journey, and I'm grateful for the lessons learned along the way. 🌱",

                "Today was all about self-care. I treated myself to a spa day and felt so rejuvenated afterward. Everyone deserves a little pampering! 💆‍♀️",

                "I’ve been reconnecting with nature lately. Going for walks in the park and enjoying the fresh air has been so refreshing for my soul. 🌳",

                "I finally tackled my fear of public speaking by joining a local Toastmasters club. It’s been a rewarding experience to step out of my comfort zone! 🎤",

                "Today, I had a heartwarming experience while volunteering at the animal shelter. Spending time with the animals and seeing them get adopted was so fulfilling! 🐶",

                "Went to a local farmers' market today and enjoyed fresh produce and homemade goodies. Supporting local farmers is so important! 🍅",

                "I’ve been exploring mindfulness practices, and they’ve truly helped me find balance in my busy life. A few moments of meditation can make a big difference. 🧘‍♂️",

                "The book I’m currently reading is a thought-provoking thriller that keeps me on the edge of my seat. I love getting lost in a good story! 📖",

                "Today reminded me of the importance of kindness. I tried to spread positivity by complimenting strangers and brightening their day. Small acts make a big impact! 😊",

                "Spent the day with family, reminiscing about old times and creating new memories. Family is everything! ❤️",

                "Feeling incredibly grateful for the amazing people in my life. Each one brings something special, and I cherish our connections. 💖",

                "Taking time to appreciate the beauty around us is so important. I spent the afternoon in the park, soaking in the sights and sounds of nature. 🌼",

                "Had a wonderful day at the beach, soaking up the sun and enjoying the waves. It's moments like these that remind me to appreciate life! 🌊",

                "Today was one of those rare days where everything aligned perfectly. I accomplished my tasks, connected with friends, and felt a sense of peace. 🌌",

                "I've been trying to live more intentionally. Making small changes in my daily routine has led to a more fulfilling life. Every moment counts! ⏳"
        };

        // Randomly select a type of text (short, medium, or long)
        int type = (int) (Math.random() * 3);
        String randomText;

        switch (type) {
            case 0:
                randomText = shortTexts[(int) (Math.random() * shortTexts.length)];
                break;
            case 1:
                randomText = mediumTexts[(int) (Math.random() * mediumTexts.length)];
                break;
            case 2:
                randomText = longTexts[(int) (Math.random() * longTexts.length)];
                break;
            default:
                randomText = ""; // Default case to avoid compilation error
        }

        return randomText;
    }
}