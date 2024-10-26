package vn.edu.ou.zalo.domain.impl;

import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.repositories.IPostRepository;
import vn.edu.ou.zalo.data.repositories.IStoryRepository;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetPostsUseCase implements IGetListUseCase<Post> {
    IPostRepository postRepository;

    @Inject
    public GetPostsUseCase(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> execute() {
        return postRepository.getPosts();
    }
}
