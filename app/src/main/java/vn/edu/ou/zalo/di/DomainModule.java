package vn.edu.ou.zalo.di;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.di.qualifiers.AddedFriends;
import vn.edu.ou.zalo.domain.ICreateUseCase;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.SignUpUseCase;
import vn.edu.ou.zalo.domain.impl.GetChatRoomsUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetDetailChatRoomUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetFriendSuggestionsImpl;
import vn.edu.ou.zalo.domain.impl.GetMessagesUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetPostsUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetSortedFriendsImpl;
import vn.edu.ou.zalo.domain.impl.GetStoriesUseCaseImpl;

@dagger.Module
@InstallIn(SingletonComponent.class)
public abstract class DomainModule {
    @Binds
    @Singleton
    abstract IGetListUseCase<ChatRoom> bindGetChatRoomsUseCase(GetChatRoomsUseCaseImpl getChatRoomsUseCase);

    @Binds
    @Singleton
    abstract IGetListUseCase<User> bindGetFriendSuggestions(GetFriendSuggestionsImpl getFriendSuggestionsUseCase);

    @Binds
    @Singleton
    @AddedFriends
    abstract IGetListUseCase<User> bindGetSortedFriends(GetSortedFriendsImpl getSortedFriends);

    @Binds
    @Singleton
    abstract IGetListUseCase<Story> bindGetStories(GetStoriesUseCaseImpl getStoriesUseCaseImpl);

    @Binds
    @Singleton
    abstract IGetListUseCase<Post> bindGetPosts(GetPostsUseCaseImpl getPostsUseCaseImpl);

    @Binds
    @Singleton
    abstract IGetListUseCase<Message> bindGetMessages(GetMessagesUseCaseImpl getMessagesUseCaseImpl);

    @Binds
    @Singleton
    abstract IGetDetailUseCase<ChatRoom> bindGetDetailChatRoom(GetDetailChatRoomUseCaseImpl getDetailChatRoomUseCase);

    @Binds
    @Singleton
    abstract ICreateUseCase<User, Void> bindCreateUserUseCase(SignUpUseCase createUserUseCase);
}
