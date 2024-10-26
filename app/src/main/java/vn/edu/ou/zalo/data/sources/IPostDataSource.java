package vn.edu.ou.zalo.data.sources;

import java.util.List;

import vn.edu.ou.zalo.data.models.Post;

public interface IPostDataSource {
    List<Post> getPosts();
}
