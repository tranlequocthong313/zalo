package vn.edu.ou.zalo.di;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.ICreateUseCase;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.AddFriendUseCase;
import vn.edu.ou.zalo.domain.impl.GetMessagesUseCase;
import vn.edu.ou.zalo.domain.impl.SearchUserUseCase;
import vn.edu.ou.zalo.domain.impl.SignUpUseCase;
import vn.edu.ou.zalo.domain.impl.GetChatRoomsUseCase;
import vn.edu.ou.zalo.domain.impl.GetDetailChatRoomUseCase;
import vn.edu.ou.zalo.domain.impl.GetPostsUseCase;
import vn.edu.ou.zalo.domain.impl.GetSortedFriends;
import vn.edu.ou.zalo.domain.impl.GetStoriesUseCase;

@dagger.Module
@InstallIn(SingletonComponent.class)
public abstract class DomainModule {
    @Binds
    @Singleton
    abstract IGetDetailUseCase<ChatRoom> bindGetDetailChatRoom(GetDetailChatRoomUseCase getDetailChatRoomUseCase);

    @Binds
    @Singleton
    abstract ICreateUseCase<User, Void> bindCreateUserUseCase(SignUpUseCase createUserUseCase);

    @Binds
    @Singleton
    abstract ICreateUseCase<User, Friendship> bindAddFriendUseCase(AddFriendUseCase addFriendUseCase);

    @Binds
    @Singleton
    abstract IGetListUseCase<ChatRoom> bindGetChatRoomsUseCase(GetChatRoomsUseCase getChatRoomsUseCase);

    @Binds
    @Singleton
    abstract IGetListUseCase<User> bindGetSortedFriends(GetSortedFriends getSortedFriends);

    @Binds
    @Singleton
    abstract IGetListUseCase<Story> bindGetStories(GetStoriesUseCase getStoriesUseCase);

    @Binds
    @Singleton
    abstract IGetListUseCase<Post> bindGetPosts(GetPostsUseCase getPostsUseCase);

    @Binds
    @Singleton
    abstract IGetListUseCase<Message> bindGetMessages(GetMessagesUseCase getMessagesUseCase);

    @Binds
    @Singleton
    abstract IGetListUseCase<User> bindSearchUserUseCase(SearchUserUseCase searchUserUseCase);
}
