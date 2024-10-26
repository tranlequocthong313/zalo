package vn.edu.ou.zalo.data.sources.fake;

import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.sources.IPostDataSource;
import vn.edu.ou.zalo.data.sources.IStoryDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;

public class PostFakeDataSourceImpl implements IPostDataSource {
    private final IUserDataSource userDataSource;
    private final Random random = new Random();

    @Inject
    public PostFakeDataSourceImpl(IUserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();

        // Create some sample users to set as authors
        List<User> users = userDataSource.getUsers();

        for (int i = 0; i < 20; i++) {
            Post post = new Post();

            // Set a random author from the list of users
            post.setAuthor(users.get(random.nextInt(users.size())));

            // Generate random text content
            post.setTextContent(generateRandomTextContent());

            // Generate random image URLs
            post.setImageUrls(generateRandomImageUrls());

            // Set random like and comment counts
            post.setLikeCount(random.nextInt(200)); // Up to 200 likes
            post.setCommentCount(random.nextInt(50)); // Up to 50 comments

            // Randomly assign visibility
            post.setVisibility(Post.Visibility.values()[random.nextInt(Post.Visibility.values().length)]);

            // Set a random created timestamp within the last month
            post.setCreatedAt(generateRandomTimestamp());

            // Add the post to the list
            posts.add(post);
        }

        return posts;
    }

    private String generateRandomTextContent() {
        String[] shortTexts = {
                "What a beautiful day! 😊",
                "Hello world! 🌍",
                "Feeling grateful today. 🙏",
                "Just had the best coffee! ☕",
                "Can't wait for the weekend! 🎉",
                "Today is a great day! ☀️",
                "Excited for the upcoming trip! ✈️",
                "Feeling blessed. 🙌",
                "Work hard, play harder! 💪",
                "Happy to be alive! 😄",
                "Chasing dreams! 🌟",
                "Let’s make the most of today! 🌈",
                "Life is good! 😊",
                "Just finished a great workout! 🏋️‍♂️",
                "Starting the day with a smile! 😁",
                "Ready to conquer the world! 🌏",
                "Grateful for this moment! 💖",
                "Good vibes only! ✌️",
                "Just chilling! 🌴",
                "Living my best life! 🌼",
                "Enjoying the little things! 🍀",
                "Smiling through it all! 😌",
                "So much to be thankful for! 🌺",
                "Keep smiling! 😃",
                "Hello sunshine! ☀️",
                "Stay positive! 🌻",
                "Today feels magical! ✨",
                "Can't stop smiling! 😍",
                "Cheers to new beginnings! 🍾",
                "Just breathe! 🌬️",
                "Wishing everyone a wonderful day! 🌟"
        };

        String[] mediumTexts = {
                "I went on a nice hike today and the view was amazing! Nature really has a way of refreshing the soul. 🌲",
                "Had a productive day at work, and now it's time to relax with a good book. 📚",
                "Exploring new places is always exciting. So many wonderful things to see and do! 🌍",
                "Spent the afternoon at the park, enjoying the fresh air and sunshine. 🌞",
                "Feeling inspired by all the creativity around me. 🎨",
                "Just got back from a lovely vacation. I feel rejuvenated! ✈️",
                "Cooking is my therapy. Today I made a delicious pasta dish! 🍝",
                "Life is a journey, and I'm loving every moment of it! 🚀",
                "I learned something new today, and I'm feeling accomplished! 🎓",
                "Dancing like nobody's watching! 💃",
                "The sunset was breathtaking tonight. 🌅",
                "Had a fun day out with friends, laughing and making memories. 🎉",
                "Every day is a new opportunity to grow and learn. 🌱",
                "Grateful for supportive friends who always lift me up! 💕",
                "Today was tough, but I’m proud of how I handled it. 💪",
                "Music has the power to change your mood instantly! 🎶",
                "Just finished a great movie night with popcorn and laughs. 🍿",
                "Taking time to appreciate the little things in life. 🌸",
                "I love rainy days, perfect for a cozy blanket and a cup of tea. ☕",
                "Remember to pause and breathe. Life is beautiful! 🌼",
                "Celebrating small victories is important! 🎊",
                "Feeling motivated to chase my dreams! 🌈",
                "Nature walks are the best way to clear my mind. 🌳",
                "Learning to be patient with myself. Growth takes time. ⏳",
                "Trying to find balance in life. It’s not always easy! ⚖️",
                "Feeling accomplished after a long week! Time for a treat! 🍰",
                "Life is full of ups and downs, but I choose to stay positive. 😊",
                "Taking a moment to reflect on all the good things in my life. 🥰",
                "Every sunset brings the promise of a new dawn. 🌅",
                "Adventure awaits! Let’s explore! 🌍",
                "Feeling inspired by all the positivity around me! 🌟",
                "Today, I choose happiness! 🎈",
                "Grateful for the love and support of my family. ❤️"
        };


        String[] longTexts = {
                "Today was an unforgettable experience. I went hiking in the mountains and the scenery was breathtaking! "
                        + "The weather was perfect, the air was fresh, and the sights were beautiful. I feel so lucky to live near "
                        + "such amazing nature spots. Can't wait for the next adventure! 🌄",

                "It's been a long journey, but I'm finally at a place where I feel comfortable and happy with my career path. "
                        + "Looking back, there were many moments of doubt, but perseverance and the support of friends and family made "
                        + "all the difference. Grateful for everyone who was part of this journey! 🙏",

                "I've been thinking a lot about the power of positive thinking. When we focus on the good things in our lives, "
                        + "even during tough times, it really does change our perspective. I'm trying to be more mindful of this daily "
                        + "and appreciate all the little things. 💖",

                "This weekend, I decided to disconnect from technology and spend some time in nature. It was refreshing to unplug "
                        + "and just be present. I saw some incredible views and even spotted a few deer! 🦌",

                "I've recently started journaling, and it has been a game changer for my mental health. Writing down my thoughts "
                        + "helps me process my emotions and clear my mind. Highly recommend it to anyone looking for a positive outlet! 📝",

                "Today, I tried a new recipe for dinner, and it turned out amazing! Cooking is such a creative outlet for me, and I love "
                        + "experimenting with different flavors and ingredients. 🍽️",

                "I had a long conversation with an old friend today, and it reminded me of how important it is to nurture our relationships. "
                        + "Life is too short to take people for granted! ❤️",

                "Volunteering at the local shelter has been one of the most rewarding experiences. Giving back to the community fills my heart "
                        + "with joy and gratitude. 🥰",

                "The book I'm currently reading is incredibly thought-provoking. It's amazing how literature can transport us to different worlds! 📖",

                "Today was one of those days where everything just clicked. I accomplished my goals, had meaningful conversations, and felt "
                        + "like I was in sync with the universe. 🌌",

                "I recently picked up yoga, and it has transformed my life. The physical and mental benefits are incredible! I feel more centered "
                        + "and at peace. 🧘‍♀️",

                "Sometimes, all we need is a little bit of kindness to brighten our day. A stranger smiled at me today, and it made me realize the power of simple gestures. 😊",

                "I went to a concert last night, and the energy was electric! There's something magical about live music that brings people together. 🎤",

                "As I walked along the beach today, I took a moment to reflect on my journey. Life has its challenges, but it's also filled with "
                        + "beauty and love. 🌊",

                "I've been exploring new hobbies lately, and it's been refreshing to step out of my comfort zone. Learning new skills keeps life "
                        + "exciting! 🎨",

                "Sometimes, we just need to take a step back and appreciate the beauty around us. Nature has a way of calming our minds and "
                        + "refreshing our spirits. 🌺",

                "Looking forward to the holidays! It's a time to reconnect with family and friends, reflect on the year, and create beautiful memories. 🎄",

                "Today, I volunteered to help at a local food bank. Seeing the impact of our efforts on the community is truly heartwarming. 💖",

                "Every moment is a chance to create new memories. I'm grateful for the opportunities life presents, and I strive to make the most of each day! 🌟",

                "I took a spontaneous road trip this weekend, and it was one of the best decisions ever! Sometimes, the unplanned adventures turn out to be the most memorable. 🚗",

                "I've been trying to incorporate more mindfulness into my daily routine. Taking a few moments each day to breathe and reflect has made a huge difference. 🧘‍♂️",

                "Today reminded me of the importance of self-care. It's vital to take time for ourselves and recharge. Whether it's a long bath or a good book, do what makes you happy! 🛁"
        };


        // Randomly select one text from either short, medium, or long texts
        int type = random.nextInt(3);
        switch (type) {
            case 0:
                return shortTexts[random.nextInt(shortTexts.length)];
            case 1:
                return mediumTexts[random.nextInt(mediumTexts.length)];
            case 2:
                return longTexts[random.nextInt(longTexts.length)];
            default:
                return ""; // In case of error
        }
    }

    private List<String> generateRandomImageUrls() {
        List<String> imageUrls = new ArrayList<>();
        int imageCount = random.nextInt(4); // 0 to 3 images

        for (int i = 0; i < imageCount; i++) {
            imageUrls.add("https://random.imagecdn.app/1920/1080");
        }

        return imageUrls.isEmpty() ? null : imageUrls;
    }

    private long generateRandomTimestamp() {
        // Current time
        long now = System.currentTimeMillis();

        // Generate a random number of days (up to 30 days in the past)
        long daysInMillis = random.nextInt(30) * DateUtils.DAY_IN_MILLIS;

        // Return a timestamp within the last 30 days
        return now - daysInMillis;
    }
}