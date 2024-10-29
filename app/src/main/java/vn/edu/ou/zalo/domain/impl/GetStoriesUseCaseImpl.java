package vn.edu.ou.zalo.domain.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.repositories.IStoryRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetStoriesUseCaseImpl implements IGetListUseCase<Story> {
    IStoryRepository storyRepository;

    @Inject
    public GetStoriesUseCaseImpl(IStoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public List<Story> execute() {
        return storyRepository.getStories();
    }

    @Override
    public List<Story> execute(Map<String, String> query) {
        return storyRepository.getStories();
    }
}
