package vn.edu.ou.zalo.domain.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.repositories.IStoryRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class GetStoriesUseCase {
    IStoryRepository storyRepository;

    @Inject
    public GetStoriesUseCase(IStoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }


    public void execute(Map<String, String> query, IDomainCallback<List<Story>> callback) {
        storyRepository.getStories(query, new IRepositoryCallback<List<Story>>() {
            @Override
            public void onSuccess(List<Story> data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
