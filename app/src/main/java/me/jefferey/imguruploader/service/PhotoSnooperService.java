package me.jefferey.imguruploader.service;

import android.app.Service;
import android.content.Intent;
import android.os.FileObserver;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.path.android.jobqueue.JobManager;

import java.util.ArrayList;

import javax.inject.Inject;

import me.jefferey.imguruploader.UploaderApplication;
import me.jefferey.imguruploader.imgur.network.ImageUploadJob;
import me.jefferey.imguruploader.utils.ImageFileObserver;

/**
 * Created by jetpeter on 8/11/15.
 *
 * Service that listens for photos saved to given directories and uploads them to imgur
 */
public class PhotoSnooperService extends Service implements ImageFileObserver.ImageCreatedListener {

    public static final String TAG = "PhotoSnooperService";

    public final ArrayList<ImageFileObserver> mFileObservers = new ArrayList<>();


    public static final String CAMERA_PATH = "/DCIM/Camera/";
    public static final String SCREENSHOT_PATH = "/Pictures/Screenshots/";

    @Inject JobManager mJobManager;


    public void onCreate() {
        super.onCreate();
        UploaderApplication.getMainComponent().inject(this);
        ImageFileObserver cameraObserver = new ImageFileObserver(CAMERA_PATH, this);
        cameraObserver.startWatching();
        mFileObservers.add(cameraObserver);

        ImageFileObserver screenshotObserver = new ImageFileObserver(SCREENSHOT_PATH, this);
        screenshotObserver.startWatching();
        mFileObservers.add(screenshotObserver);
    }

    public void onDestroy() {
        for (FileObserver fileObserver : mFileObservers) {
            fileObserver.stopWatching();
        }
        super.onDestroy();
    }

    @Override
    public void onImageCreated(String imagePath, String imageName) {
        ImageUploadJob imageUploadJob = new ImageUploadJob(imagePath + imageName);
        mJobManager.addJob(imageUploadJob);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
