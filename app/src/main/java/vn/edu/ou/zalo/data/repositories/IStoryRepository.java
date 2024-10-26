package vn.edu.ou.zalo.data.repositories;

import java.util.List;

import vn.edu.ou.zalo.data.models.Story;

public interface IStoryRepository {
    List<Story> getStories();
}
