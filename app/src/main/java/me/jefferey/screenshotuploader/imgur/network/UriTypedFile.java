package me.jefferey.screenshotuploader.imgur.network;

import android.content.ContentResolver;

import java.io.File;

import retrofit.mime.TypedFile;

/**
 * Created by jetpeter on 8/4/15.
 */
public class UriTypedFile extends TypedFile {


    /**
     * Constructs a new typed file.
     * @param mimeType Mime type of the file
     * @param file
     * @throws NullPointerException if file or mimeType is null
     */
    public UriTypedFile(String mimeType, File file) {
        super(mimeType, file);

    }
}
