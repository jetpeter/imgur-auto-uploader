package me.jefferey.imguruploader.imgur.network;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.squareup.otto.Bus;

import java.io.File;

import me.jefferey.imguruploader.MainComponent;
import me.jefferey.imguruploader.UploaderApplication;
import me.jefferey.imguruploader.imgur.model.Image;
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


    public ImageUploadJob(@NonNull String imagePath) {
        super(new Params(PRIORITY).requireNetwork().persist());
        mImageUriPath = imagePath;
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
        String mimeType = MimeTypeMap.getFileExtensionFromUrl(mImageUriPath);
        TypedFile typedFile = new TypedFile("image/" + mimeType, imageFile);
        MainComponent mainComponent = UploaderApplication.getMainComponent();
        ImgurService imgurService = mainComponent.provideImgurService();
        UploadResponse response = imgurService.uploadImage(typedFile);
        if (response.success) {
            postImage(response.data);
        }
    }

    /**
     * Post the newly created images to the message bus
     * @param image newly created Image instance
     */
    private void postImage(@NonNull final Image image) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                MainComponent mainComponent = UploaderApplication.getMainComponent();
                Bus bus = mainComponent.provideBus();
                bus.post(image);
            }
        });
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
