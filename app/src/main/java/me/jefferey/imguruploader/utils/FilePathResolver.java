package me.jefferey.imguruploader.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by jpetersen on 8/7/15.
 *
 * Converts URIs to full image path on the device.
 */
public class FilePathResolver {

    private final ContentResolver mContentResolver;

    public FilePathResolver(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    public String getImagePathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = mContentResolver.query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
