package vn.edu.ou.zalo.data.repositories.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.repositories.IPostRepository;
import vn.edu.ou.zalo.data.sources.IPostDataSource;

public class PostRepositoryImpl implements IPostRepository {
    private final IPostDataSource postDataSource;

    @Inject
    public PostRepositoryImpl(IPostDataSource postDataSource) {
        this.postDataSource = postDataSource;
    }

    @Override
    public List<Post> getPosts() {
        return postDataSource.getPosts();
    }
}