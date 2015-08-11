package me.jefferey.imguruploader.imgur.network;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.io.File;

import javax.inject.Inject;

import me.jefferey.imguruploader.MainComponent;
import me.jefferey.imguruploader.UploaderApplication;
import me.jefferey.imguruploader.imgur.model.UploadResponse;
import me.jefferey.imguruploader.utils.FilePathResolver;
import retrofit.mime.TypedFile;

/**
 * Created by jetpeter on 8/4/15.
 *
 * Priority Request Queue job for uploading images to imgur
 */
public class ImageUploadJob extends Job {

    public static final String TAG = "ImageUploadJob";
    public static final int PRIORITY = 1;

    public String mImageUriPath;

    @Inject transient ImgurService mImgurService;

    public ImageUploadJob(@NonNull String iamgePath) {
        super(new Params(PRIORITY).requireNetwork().persist());
        mImageUriPath = iamgePath;
    }

    public ImageUploadJob(@NonNull Uri imageUri) {
        super(new Params(PRIORITY).requireNetwork().persist());
        MainComponent mainComponent = UploaderApplication.getMainComponent();
        FilePathResolver filePathResolver = mainComponent.provideFilePathResolver();
        mImageUriPath = filePathResolver.getImagePathFromURI(imageUri);
    }

    @Override
    public void onAdded() {
        Log.v(TAG, "Upload Started");
    }

    @Override
    public void onRun() throws Throwable {
        File imageFile = new File(mImageUriPath);
        TypedFile typedFile = new TypedFile("image/jpg", imageFile);
        MainComponent mainComponent = UploaderApplication.getMainComponent();
        ImgurService imgurService = mainComponent.provideImgurService();
        UploadResponse response = imgurService.uploadImage(typedFile);
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
