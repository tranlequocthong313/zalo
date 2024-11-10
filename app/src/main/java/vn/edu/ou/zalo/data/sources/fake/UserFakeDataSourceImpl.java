package vn.edu.ou.zalo.data.sources.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IUserDataSource;

public class UserFakeDataSourceImpl implements IUserDataSource {
    private static final String[] FIRST_NAMES = {"Alice", "John", "Bob", "Lionel", "Jane", "Emma", "Michael", "Sophia"};
    private static final String[] LAST_NAMES = {"Doe", "Smith", "Brown", "Johnson", "Williams", "Jones", "Davis", "Miller"};
    private static final String[] BIOS = {"Loves coding", "Avid reader", "Coffee enthusiast", "Adventure seeker", "Tech geek", "Nature lover"};

    private final Random random = new Random();
    private final List<User> users = new ArrayList<>();

    @Inject
    public UserFakeDataSourceImpl() {
        initUsers();
    }

    private void initUsers() {
        // Generate a random number of users (between 10 and 20)
        for (int i = 0; i < 20; i++) {
            User user = generateRandomUser();
            user.setId(UUID.randomUUID().toString());
            users.add(user);
        }
    }

    @Override
    public void getUsers(IRepositoryCallback<List<User>> callback) {
        callback.onSuccess(users);
    }

    @Override
    public User getLoginUser() {
        return users.get(0); // TODO: for dev purpose
    }

    private User generateRandomUser() {
        User user = new User();

        // Set random name
        String firstName = getRandomFirstName();
        String lastName = getRandomLastName();
        user.setFullName(firstName + " " + lastName);

        // Set random phone number
        user.setPhoneNumber(generateRandomPhoneNumber());

        // Set random birthdate (between 18 and 50 years ago)
        user.setBirthdate(generateRandomBirthdate());

        // Set random gender
        user.setGender(User.Gender.values()[random.nextInt(2)]);

        // Set random bio
        user.setBio(getRandomBio());

        // Set avatar and background URLs using provided APIs
        user.setAvatarUrl(generateRandomAvatarUrl());
        user.setBackgroundUrl(generateRandomBackgroundUrl());

        // Set random last login timestamp (within the last 7 days)
        user.setLastLogin(generateRandomLastLogin());

        // Set random friend count
        user.setFriendCount(generateRandomFriendCount());

        user.setIsOnline(random.nextInt(10) % 2 == 0);

        return user;
    }

    private String getRandomFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    private String getRandomLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    private String generateRandomPhoneNumber() {
        return "+1-" + (100 + random.nextInt(900)) + "-" + (100 + random.nextInt(900)) + "-" + (1000 + random.nextInt(9000));
    }

    private long generateRandomBirthdate() {
        // Birthdate between 18 and 50 years ago
        long currentTime = System.currentTimeMillis();
        long minAgeMillis = 18L * 365 * 24 * 60 * 60 * 1000;
        long maxAgeMillis = 50L * 365 * 24 * 60 * 60 * 1000;
        return currentTime - (minAgeMillis + (long) (random.nextDouble() * (maxAgeMillis - minAgeMillis)));
    }

    private String getRandomBio() {
        return BIOS[random.nextInt(BIOS.length)];
    }

    private String generateRandomAvatarUrl() {
        // Using the random avatar API
        return "https://avatar.iran.liara.run/public/";
    }

    private String generateRandomBackgroundUrl() {
        // Using the random background image API
        return "https://random.imagecdn.app/500/150";
    }

    private long generateRandomLastLogin() {
        // Last login within the last 7 days
        long currentTime = System.currentTimeMillis();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000;
        return currentTime - random.nextInt((int) oneWeekInMillis);
    }

    private long generateRandomFriendCount() {
        // Random friend count between 0 and 500
        return random.nextInt(501);
    }
}
