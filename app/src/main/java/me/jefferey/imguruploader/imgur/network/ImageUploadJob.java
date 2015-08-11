package me.jefferey.imguruploader.imgur.network;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.io.File;

import io.realm.Realm;
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
    public static final String NEW_IMAGE_BROADCAST = "imageCreated";
    public static final String IMAGE_ID = "imageId";
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
            saveImage(response.data);
        }
    }

    private void saveImage(@NonNull Image image) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(image);
        realm.commitTransaction();
        realm.close();
        Intent intent = new Intent(NEW_IMAGE_BROADCAST);
        intent.putExtra(IMAGE_ID, image.getId());
        MainComponent mainComponent = UploaderApplication.getMainComponent();
        LocalBroadcastManager localBroadcastManager = mainComponent.provideLocalBroadcastManager();
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
