package vn.edu.ou.zalo.domain.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.repositories.IStoryRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetStoriesUseCase implements IGetListUseCase<Story> {
    IStoryRepository storyRepository;

    @Inject
    public GetStoriesUseCase(IStoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public List<Story> execute() {
        return storyRepository.getStories();
    }
}
