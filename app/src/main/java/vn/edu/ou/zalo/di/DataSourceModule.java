package vn.edu.ou.zalo.di;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.repositories.IChatRoomRepository;
import vn.edu.ou.zalo.data.repositories.impl.ChatRoomRepositoryImpl;
import vn.edu.ou.zalo.data.sources.IChatRoomDataSource;
import vn.edu.ou.zalo.data.sources.IUserDataSource;
import vn.edu.ou.zalo.data.sources.fake.ChatRoomFakeDataSourceImpl;
import vn.edu.ou.zalo.data.sources.fake.UserFakeDataSourceImpl;
import vn.edu.ou.zalo.domain.IGetListUseCase;
import vn.edu.ou.zalo.domain.impl.GetChatRoomsUseCaseImpl;

@dagger.Module
@InstallIn(SingletonComponent.class)
public abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract IChatRoomDataSource bindChatRoomFakeDataSource(ChatRoomFakeDataSourceImpl chatRoomFakeDataSource);

    @Binds
    @Singleton
    abstract IUserDataSource bindUserFakeDataSource(UserFakeDataSourceImpl userFakeDataSource);
}
