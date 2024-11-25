package vn.edu.ou.zalo.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.repositories.IAuthRepository;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IFriendshipRepository;
import vn.edu.ou.zalo.data.repositories.IMessageRepository;
import vn.edu.ou.zalo.data.repositories.IPostRepository;
import vn.edu.ou.zalo.data.repositories.IStoryRepository;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.data.repositories.impl.AuthRepository;
import vn.edu.ou.zalo.data.repositories.impl.ChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.impl.FriendshipRepository;
import vn.edu.ou.zalo.data.repositories.impl.MessageRepository;
import vn.edu.ou.zalo.data.repositories.impl.PostRepository;
import vn.edu.ou.zalo.data.repositories.impl.StoryRepository;
import vn.edu.ou.zalo.data.repositories.impl.UserRepository;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract IChatRoomRepository bindChatRoomRepository(ChatRoomRepository chatRoomRepository);

    @Binds
    @Singleton
    abstract IUserRepository bindUserRepository(UserRepository userRepository);

    @Binds
    @Singleton
    abstract IStoryRepository bindStoryRepository(StoryRepository storyRepository);

    @Binds
    @Singleton
    abstract IPostRepository bindPostRepository(PostRepository postRepository);

    @Binds
    @Singleton
    abstract IMessageRepository bindMessageRepository(MessageRepository messageRepository);

    @Binds
    @Singleton
    abstract IAuthRepository bindAuthRemoteRepository(AuthRepository authRepository);

    @Binds
    @Singleton
    abstract IFriendshipRepository bindFriendshipRepository(FriendshipRepository friendshipRepository);
}
