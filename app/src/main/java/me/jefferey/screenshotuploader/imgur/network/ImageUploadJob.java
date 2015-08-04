package me.jefferey.screenshotuploader.imgur.network;

import android.net.Uri;
import android.util.Log;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.io.File;

import javax.inject.Inject;

import me.jefferey.screenshotuploader.ScreenshotUploaderApplication;
import me.jefferey.screenshotuploader.imgur.model.UploadResponse;
import retrofit.mime.TypedFile;

/**
 * Created by jetpeter on 8/4/15.
 *
 * Priority Request Queue job for uploading images to imgur
 */
public class ImageUploadJob extends Job {

    public static final String TAG = "ImageUploadJob";
    public static final int PRIORITY = 1;

    public final Uri mImageUri;

    @Inject ImgurService mImgurService;

    public ImageUploadJob(Uri imageUri) {
        super(new Params(PRIORITY).requireNetwork().persist());
        ScreenshotUploaderApplication.getMainComponent().inject(this);
        mImageUri = imageUri;
    }

    @Override
    public void onAdded() {
        Log.v(TAG, "Upload Started");
    }

    @Override
    public void onRun() throws Throwable {
        File imageFile = new File(mImageUri.getPath());
        TypedFile typedFile = new TypedFile("image/jpg", imageFile);
        UploadResponse response = mImgurService.uploadImage(typedFile);
        Log.v(TAG, "Upload Started Finished. Success: " + response.success);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
