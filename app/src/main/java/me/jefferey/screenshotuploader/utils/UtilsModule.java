package me.jefferey.screenshotuploader.utils;

import android.content.Context;

import com.squareup.otto.Bus;

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
    public PreferencesManager providePreferencesManager() {
        return new PreferencesManager(mContext);
    }

    @Provides @Singleton
    public Bus provideBus() {
        return new Bus();
    }
}
