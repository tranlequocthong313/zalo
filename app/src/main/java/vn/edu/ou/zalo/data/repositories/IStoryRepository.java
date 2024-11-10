package vn.edu.ou.zalo.data.repositories;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.Story;

public interface IStoryRepository {
    void getStories(Map<String, String> query, IRepositoryCallback<List<Story>> IRepositoryCallback);
}
