package vn.edu.ou.zalo.data.sources.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.sources.IStoryDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;

public class StoryFakeDataSourceImpl implements IStoryDataSource {
    private final IUserDataSource userDataSource;
    private final Random random = new Random();

    @Inject
    public StoryFakeDataSourceImpl(IUserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public List<Story> getStories() {
        List<User> users = userDataSource.getUsers();
        List<Story> stories = new ArrayList<>();

        int storyCount = 10 + random.nextInt(11); // Generates between 10 and 20 stories

        for (int i = 0; i < storyCount; i++) {
            Story story = new Story();
            story.setId(UUID.randomUUID().toString());
            story.setImageUrl("https://loremflickr.com/240/320/");
            story.setAuthor(users.get(random.nextInt(users.size())));
            story.setViewCount(random.nextInt(1000)); // Random view count for demonstration
            story.setStatus(Story.Status.values()[random.nextInt(2)]); // 0 or 1
            story.setVisibility(Story.Visibility.values()[random.nextInt(3)]); // 0, 1, or 2

            stories.add(story);
        }

        return stories;
    }
}
