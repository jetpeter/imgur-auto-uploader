package me.jefferey.screenshotuploader;

import android.app.Application;

import me.jefferey.screenshotuploader.utils.UtilsModule;

/**
 * Created by jpetersen on 7/19/15.
 */
public class ScreenshotUploaderApplication extends Application {

    private static MainComponent sMainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sMainComponent = DaggerMainComponent.builder()
                .utilsModule(new UtilsModule(getApplicationContext()))
                .build();
    }

    public static MainComponent getMainComponent() {
        return sMainComponent;
    }
}
