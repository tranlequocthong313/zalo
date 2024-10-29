package vn.edu.ou.zalo.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.data.repositories.IPostRepository;
import vn.edu.ou.zalo.data.repositories.IStoryRepository;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.data.repositories.impl.ChatRoomRepositoryImpl;
import vn.edu.ou.zalo.data.repositories.impl.MessageRepositoryImpl;
import vn.edu.ou.zalo.data.repositories.impl.PostRepositoryImpl;
import vn.edu.ou.zalo.data.repositories.impl.StoryRepositoryImpl;
import vn.edu.ou.zalo.data.repositories.impl.UserRepositoryImpl;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract IChatRoomRepository bindChatRoomRepository(ChatRoomRepositoryImpl chatRoomRepository);

    @Binds
    @Singleton
    abstract IUserRepository bindUserRepository(UserRepositoryImpl userRepository);

    @Binds
    @Singleton
    abstract IStoryRepository bindStoryRepository(StoryRepositoryImpl storyRepository);

    @Binds
    @Singleton
    abstract IPostRepository bindPostRepository(PostRepositoryImpl postRepository);

    @Binds
    @Singleton
    abstract IMessageRepository bindMessageRepository(MessageRepositoryImpl messageRepository);
}
