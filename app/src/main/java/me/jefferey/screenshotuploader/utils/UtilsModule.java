package me.jefferey.screenshotuploader.utils;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jpetersen on 7/19/15.
 */
@Module
public class UtilsModule {

    private final Context mContext;

    public UtilsModule(Context context) {
        mContext = context;
    }

    @Provides @Singleton
    public PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }

    @Provides @Singleton
    public Gson provideGson() {
        return new Gson();
    }

}
