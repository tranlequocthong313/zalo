package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.repositories.IStoryRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IStoryDataSource;

public class StoryRepositoryImpl implements IStoryRepository {
    private final IStoryDataSource storyDataSource;

    @Inject
    public StoryRepositoryImpl(IStoryDataSource storyDataSource) {
        this.storyDataSource = storyDataSource;
    }

    @Override
    public void getStories(Map<String, String> query, IRepositoryCallback<List<Story>> IRepositoryCallback) {
        storyDataSource.getStories(query, new IRepositoryCallback<List<Story>>() {
            @Override
            public void onSuccess(List<Story> data) {
                IRepositoryCallback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                IRepositoryCallback.onFailure(e);
            }
        });
    }
}