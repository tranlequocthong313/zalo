package vn.edu.ou.zalo.domain.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.repositories.IPostRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.IGetListUseCase;

public class GetPostsUseCaseImpl implements IGetListUseCase<Post> {
    IPostRepository postRepository;

    @Inject
    public GetPostsUseCaseImpl(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void execute(Map<String, String> query, IDomainCallback<List<Post>> callback) {
        postRepository.getPosts(query, new IRepositoryCallback<List<Post>>() {
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
