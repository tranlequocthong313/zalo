package vn.edu.ou.zalo.di;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;
import vn.edu.ou.zalo.data.sources.ICallDataSource;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.data.sources.IFileStorageDataSource;
import vn.edu.ou.zalo.data.sources.IFriendshipDataSource;
import vn.edu.ou.zalo.data.sources.IMessageDataSource;
import vn.edu.ou.zalo.data.sources.IPostDataSource;
import vn.edu.ou.zalo.data.sources.IStoryDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;
import vn.edu.ou.zalo.data.sources.fake.ChatRoomFakeDataSource;
import vn.edu.ou.zalo.data.sources.fake.MessageFakeDataSource;
import vn.edu.ou.zalo.data.sources.fake.PostFakeDataSource;
import vn.edu.ou.zalo.data.sources.fake.StoryFakeDataSource;
import vn.edu.ou.zalo.data.sources.fake.UserFakeDataSource;
import vn.edu.ou.zalo.data.sources.remote.AuthRemoteDataSource;
import vn.edu.ou.zalo.data.sources.remote.CallWebRTCDataSource;
import vn.edu.ou.zalo.data.sources.remote.ChatRoomRemoteDataSource;
import vn.edu.ou.zalo.data.sources.remote.FileStorageCloudinaryDataSource;
import vn.edu.ou.zalo.data.sources.remote.FriendshipRemoteDataSource;
import vn.edu.ou.zalo.data.sources.remote.MessageRemoteDataSource;
import vn.edu.ou.zalo.data.sources.remote.UserRemoteDataSource;
import vn.edu.ou.zalo.di.qualifiers.Fake;

@dagger.Module
@InstallIn(SingletonComponent.class)
public abstract class DataSourceModule {
    @Binds
    @Singleton
    @Fake
    abstract IChatRoomDataSource bindChatRoomFakeDataSource(ChatRoomFakeDataSource chatRoomFakeDataSource);

    @Binds
    @Singleton
    abstract IChatRoomDataSource bindChatRoomRemoteDataSource(ChatRoomRemoteDataSource chatRoomRemoteDataSource);

    @Binds
    @Singleton
    @Fake
    abstract IUserDataSource bindUserFakeDataSource(UserFakeDataSource userFakeDataSource);

    @Binds
    @Singleton
    abstract IUserDataSource bindUserRemoteDataSource(UserRemoteDataSource userRemoteDataSource);

    @Binds
    @Singleton
    abstract IStoryDataSource bindStoryFakeDataSource(StoryFakeDataSource storyFakeDataSource);

    @Binds
    @Singleton
    abstract IPostDataSource bindPostFakeDataSource(PostFakeDataSource postFakeDataSource);

    @Binds
    @Singleton
    @Fake
    abstract IMessageDataSource bindMessageFakeDataSource(MessageFakeDataSource messageFakeDataSource);

    @Binds
    @Singleton
    abstract IAuthDataSource bindAuthRemoteDataSource(AuthRemoteDataSource authDataSource);

    @Binds
    @Singleton
    abstract IFriendshipDataSource bindFriendshipRemoteDataSource(FriendshipRemoteDataSource friendshipRemoteDataSource);

    @Binds
    @Singleton
    abstract IMessageDataSource bindMessageRemoteDataSource(MessageRemoteDataSource messageRemoteDataSource);

    @Binds
    @Singleton
    abstract IFileStorageDataSource bindFileStorageDataSource(FileStorageCloudinaryDataSource fileStorageCloudinaryDataSource);

//    @Binds
//    @Singleton
//    abstract ICallDataSource bindCallDataSource(CallStringeeDataSource callStringeeDataSource);

    @Binds
    @Singleton
    abstract ICallDataSource bindCallDataSource(CallWebRTCDataSource callWebRTCDataSource);
}
