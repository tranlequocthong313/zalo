package vn.edu.ou.zalo.data.sources;

import java.util.List;

import vn.edu.ou.zalo.data.models.Story;

public interface IStoryDataSource {
    List<Story> getStories();
}
