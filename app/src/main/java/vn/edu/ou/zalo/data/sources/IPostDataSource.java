package vn.edu.ou.zalo.data.sources;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IPostDataSource {
    void getPosts(Map<String, String> query, IRepositoryCallback<List<Post>> dataSourceCallback);
}
