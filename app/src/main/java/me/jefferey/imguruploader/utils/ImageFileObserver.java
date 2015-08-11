package me.jefferey.imguruploader.utils;

import android.os.FileObserver;
import android.support.annotation.NonNull;

/**
 * Created by jetpeter on 8/11/15.
 *
 * Extension of FileObserver that only watches for file creation events. When a file is created
 * the given instance of ImageCreatedListener calls onImageCreated.
 */
public class ImageFileObserver extends FileObserver {

    public static final String PNG = ".png";
    public static final String JPEG = ".jpg";


    public static final String EXTERNAL_STORAGE_DIR = android.os.Environment.getExternalStorageDirectory().toString();
    private final String mObserverPath;
    private final ImageCreatedListener mListener;

    public ImageFileObserver(@NonNull String storageDirectoryPath, @NonNull ImageCreatedListener imageCreatedListener) {
        super(EXTERNAL_STORAGE_DIR + storageDirectoryPath, FileObserver.CREATE);
        mObserverPath = EXTERNAL_STORAGE_DIR + storageDirectoryPath;
        mListener = imageCreatedListener;
    }

    @Override
    public void onEvent(int event, String fileName) {
        if (isImage(fileName)) {
            mListener.onImageCreated(mObserverPath, fileName);
        }
    }

    public boolean isImage(@NonNull String fileName) {
        return fileName.endsWith(PNG) || fileName.endsWith(JPEG);
    }

    public interface ImageCreatedListener {
        void onImageCreated(String imagePath, String imageName);
    }
}
