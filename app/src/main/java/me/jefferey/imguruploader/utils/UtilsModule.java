package me.jefferey.imguruploader.utils;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jpetersen on 7/19/15.
 *
 * Module for providing utility dependencies
 */
@Module
public class UtilsModule {

    private final Context mApplicationContext;

    public UtilsModule(Context context) {
        mApplicationContext = context;
    }

    @Provides @Singleton
    public PreferencesManager providePreferencesManager() {
        return new PreferencesManager(mApplicationContext);
    }

    @Provides @Singleton
    public Bus provideBus() {
        return new Bus();
    }

    @Provides @Singleton
    public Configuration provideConfiguration() {
        return new Configuration.Builder(mApplicationContext)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
    }

    @Provides @Singleton
    public JobManager provideJobManager(Configuration configuration) {
        return new JobManager(mApplicationContext, configuration);
    }

    @Provides
    public FilePathResolver provideFilePathResolber() {
        return new FilePathResolver(mApplicationContext.getContentResolver());
    }

    @Provides
    public LocalBroadcastManager provideLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(mApplicationContext);
    }
}
