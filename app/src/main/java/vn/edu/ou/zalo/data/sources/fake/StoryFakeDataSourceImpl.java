package vn.edu.ou.zalo.data.sources.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IStoryDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;

public class StoryFakeDataSourceImpl implements IStoryDataSource {
    private final Random random = new Random();
    private List<User> users;

    @Inject
    public StoryFakeDataSourceImpl(IUserDataSource userDataSource) {
        userDataSource.getUsers(new IRepositoryCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                users = data;
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    public void getStories(Map<String, String> query, IRepositoryCallback<List<Story>> dataSourceCallback) {
        List<Story> stories = new ArrayList<>();

        int storyCount = 10 + random.nextInt(11); // Generates between 10 and 20 stories

        for (int i = 0; i < storyCount; i++) {
            int width = random.nextInt(1200) + 50;  // Width between 50 and 1250
            int height = random.nextInt(1200) + 50; // Height between 50 and 1250
            Story story = new Story();
            story.setId(UUID.randomUUID().toString());
            story.setImageUrl("https://picsum.photos/" + width + "/" + height);
            story.setAuthor(users.get(random.nextInt(users.size())));
            story.setViewCount(random.nextInt(1000)); // Random view count for demonstration
            story.setStatus(Story.Status.values()[random.nextInt(2)]); // 0 or 1
            story.setVisibility(Story.Visibility.values()[random.nextInt(3)]); // 0, 1, or 2

            stories.add(story);
        }

        dataSourceCallback.onSuccess(stories);
    }
}
