package vn.edu.ou.zalo.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.IUserRepository;
import vn.edu.ou.zalo.data.repositories.impl.ChatRoomRepositoryImpl;
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
}
