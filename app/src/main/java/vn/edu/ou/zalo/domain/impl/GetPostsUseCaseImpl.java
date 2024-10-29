package vn.edu.ou.zalo.domain.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.repositories.IPostRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetPostsUseCaseImpl implements IGetListUseCase<Post> {
    IPostRepository postRepository;

    @Inject
    public GetPostsUseCaseImpl(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> execute() {
        return postRepository.getPosts();
    }

    @Override
    public List<Post> execute(Map<String, String> query) {
        return postRepository.getPosts();
    }
}
