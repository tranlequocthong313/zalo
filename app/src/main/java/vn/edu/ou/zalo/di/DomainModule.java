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
import vn.edu.ou.zalo.di.qualifiers.ImportantChatRooms;
import vn.edu.ou.zalo.di.qualifiers.UnimportantChatRooms;
import vn.edu.ou.zalo.domain.IGetDetailUseCase;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.GetChatRoomsUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetDetailChatRoomUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetFriendSuggestionsImpl;
import vn.edu.ou.zalo.domain.impl.GetGroupChatRoomsUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetLoginUserUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetMessagesUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetPostsUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetSortedFriendsImpl;
import vn.edu.ou.zalo.domain.impl.GetStoriesUseCaseImpl;
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
    abstract IGetListUseCase<Story> bindGetStories(GetStoriesUseCaseImpl getStoriesUseCaseImpl);

    @Binds
    @Singleton
    abstract IGetListUseCase<Post> bindGetPosts(GetPostsUseCaseImpl getPostsUseCaseImpl);

    @Binds
    @Singleton
    abstract IGetListUseCase<Message> bindGetMessages(GetMessagesUseCaseImpl getMessagesUseCaseImpl);

    @Binds
    @Singleton
    abstract IGetDetailUseCase<User> bindGetLoginUser(GetLoginUserUseCaseImpl getLoginUserUseCaseImpl);

    @Binds
    @Singleton
    abstract IGetDetailUseCase<ChatRoom> bindGetDetailChatRoom(GetDetailChatRoomUseCaseImpl getDetailChatRoomUseCase);
}
