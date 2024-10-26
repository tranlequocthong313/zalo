package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IStoryRepository;
import vn.edu.ou.zalo.data.sources.IStoryDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;

public class StoryRepositoryImpl implements IStoryRepository {
    private final IStoryDataSource storyDataSource;

    @Inject
    public StoryRepositoryImpl(IStoryDataSource storyDataSource) {
        this.storyDataSource = storyDataSource;
    }

    @Override
    public List<Story> getStories() {
        return storyDataSource.getStories();
    }
}