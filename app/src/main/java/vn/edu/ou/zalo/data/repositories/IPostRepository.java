package vn.edu.ou.zalo.data.repositories;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.Post;

public interface IPostRepository {
    void getPosts(Map<String, String> query, IRepositoryCallback<List<Post>> callback);
}
