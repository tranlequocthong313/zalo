package vn.edu.ou.zalo.data.sources;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IStoryDataSource {
    void getStories(Map<String, String> query, IRepositoryCallback<List<Story>> dataSourceCallback);
}
