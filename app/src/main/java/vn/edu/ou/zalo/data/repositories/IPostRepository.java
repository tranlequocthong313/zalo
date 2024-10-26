package vn.edu.ou.zalo.data.repositories;

import java.util.List;

import vn.edu.ou.zalo.data.models.Post;

public interface IPostRepository {
    List<Post> getPosts();
}
