package vn.edu.ou.zalo.di;


import android.content.Context;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class CloudinaryModule {
    public MediaManager init(Context context) {
        // options secure = true is causing a bug that I can't resolve yet
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "diojasks1");
        MediaManager.init(context, config);
        return MediaManager.get();
    }

    @Provides
    @Singleton
    public MediaManager provideMediaManager(@ApplicationContext Context context) {
        try {
            return MediaManager.get();
        } catch (IllegalStateException e) {
            return init(context);
        }
    }
}
