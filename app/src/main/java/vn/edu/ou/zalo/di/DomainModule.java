package vn.edu.ou.zalo.di;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.impl.ChatRoomRepositoryImpl;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.data.sources.fake.ChatRoomFakeDataSourceImpl;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.GetChatRoomsUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetFriendSuggestionsImpl;
import vn.edu.ou.zalo.domain.impl.GetGroupChatRoomsUseCaseImpl;
import vn.edu.ou.zalo.domain.impl.GetSortedFriendsImpl;
import vn.edu.ou.zalo.domain.impl.GetUnimportantChatRoomsUseCaseImpl;

import javax.inject.Qualifier;

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
}
