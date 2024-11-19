package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.repositories.IPostRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IPostDataSource;

public class PostRepository implements IPostRepository {
    private final IPostDataSource postDataSource;

    @Inject
    public PostRepository(IPostDataSource postDataSource) {
        this.postDataSource = postDataSource;
    }

    @Override
    public void getPosts(Map<String, String> query, IRepositoryCallback<List<Post>> callback) {
        postDataSource.getPosts(query, new IRepositoryCallback<List<Post>>() {
            @Override
            public void onSuccess(List<Post> data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}