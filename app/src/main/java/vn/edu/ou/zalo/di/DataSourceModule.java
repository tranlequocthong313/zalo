package vn.edu.ou.zalo.di;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.data.sources.IMessageDataSource;
import vn.edu.ou.zalo.data.sources.IPostDataSource;
import vn.edu.ou.zalo.data.sources.IStoryDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;
import vn.edu.ou.zalo.data.sources.fake.ChatRoomFakeDataSourceImpl;
import vn.edu.ou.zalo.data.sources.fake.MessageFakeDataSourceImpl;
import vn.edu.ou.zalo.data.sources.fake.PostFakeDataSourceImpl;
import vn.edu.ou.zalo.data.sources.fake.StoryFakeDataSourceImpl;
import vn.edu.ou.zalo.data.sources.fake.UserFakeDataSourceImpl;
import vn.edu.ou.zalo.data.sources.remote.AuthDataSourceImpl;
import vn.edu.ou.zalo.data.sources.remote.ChatRoomRemoteDataSourceImpl;
import vn.edu.ou.zalo.data.sources.remote.UserRemoteDataSourceImpl;
import vn.edu.ou.zalo.di.qualifiers.Fake;

@dagger.Module
@InstallIn(SingletonComponent.class)
public abstract class DataSourceModule {
    @Binds
    @Singleton
    @Fake
    abstract IChatRoomDataSource bindChatRoomFakeDataSource(ChatRoomFakeDataSourceImpl chatRoomFakeDataSource);

    @Binds
    @Singleton
    abstract IChatRoomDataSource bindChatRoomRemoteDataSource(ChatRoomRemoteDataSourceImpl chatRoomRemoteDataSource);

    @Binds
    @Singleton
    @Fake
    abstract IUserDataSource bindUserFakeDataSource(UserFakeDataSourceImpl userFakeDataSource);

    @Binds
    @Singleton
    abstract IUserDataSource bindUserRemoteDataSource(UserRemoteDataSourceImpl userRemoteDataSourceImpl);

    @Binds
    @Singleton
    abstract IStoryDataSource bindStoryFakeDataSource(StoryFakeDataSourceImpl storyFakeDataSource);

    @Binds
    @Singleton
    abstract IPostDataSource bindPostFakeDataSource(PostFakeDataSourceImpl postFakeDataSource);

    @Binds
    @Singleton
    abstract IMessageDataSource bindMessageFakeDataSource(MessageFakeDataSourceImpl messageFakeDataSource);

    @Binds
    @Singleton
    abstract IAuthDataSource bindAuthRemoteDataSource(AuthDataSourceImpl authDataSource);
}
