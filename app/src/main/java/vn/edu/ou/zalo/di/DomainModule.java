package vn.edu.ou.zalo.di;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.di.qualifiers.AddedFriends;
import vn.edu.ou.zalo.di.qualifiers.ImportantChatRooms;
import vn.edu.ou.zalo.di.qualifiers.UnimportantChatRooms;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.GetChatRoomsUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetFriendSuggestionsImpl;
import vn.edu.ou.zalo.domain.impl.GetGroupChatRoomsUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetLoginUserUseCase;
import vn.edu.ou.zalo.domain.impl.GetPostsUseCase;
import vn.edu.ou.zalo.domain.impl.GetSortedFriendsImpl;
import vn.edu.ou.zalo.domain.impl.GetStoriesUseCase;
import vn.edu.ou.zalo.domain.impl.GetUnimportantChatRoomsUseCaseImpl;

@dagger.Module
@InstallIn(SingletonComponent.class)
public abstract class DomainModule {
    @Binds
    @Singleton
    @ImportantChatRooms
    abstract IGetListUseCase<ChatRoom> bindGetChatRoomsUseCase(GetChatRoomsUseCaseImpl getChatRoomsUseCase);

    @Binds
    @Singleton
    abstract IGetListUseCase<ChatRoom> bindGetGroupChatRoomsUseCase(GetGroupChatRoomsUseCaseImpl getGroupChatRoomsUseCase);

    @Binds
    @Singleton
    @UnimportantChatRooms
    abstract IGetListUseCase<ChatRoom> bindGetUnimportantChatRoomsUseCase(GetUnimportantChatRoomsUseCaseImpl getChatRoomsUseCase);

    @Binds
    @Singleton
    abstract IGetListUseCase<User> bindGetFriendSuggestions(GetFriendSuggestionsImpl getFriendSuggestionsUseCase);

    @Binds
    @Singleton
    @AddedFriends
    abstract IGetListUseCase<User> bindGetSortedFriends(GetSortedFriendsImpl getSortedFriends);

    @Binds
    @Singleton
    abstract IGetListUseCase<Story> bindGetStories(GetStoriesUseCase getStoriesUseCase);

    @Binds
    @Singleton
    abstract IGetListUseCase<Post> bindGetPosts(GetPostsUseCase getPostsUseCase);

    @Binds
    @Singleton
    abstract IGetDetailUseCase<User> bindGetLoginUser(GetLoginUserUseCase getLoginUserUseCase);
}
