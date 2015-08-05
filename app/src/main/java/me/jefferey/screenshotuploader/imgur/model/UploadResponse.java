package me.jefferey.screenshotuploader.imgur.model;

/**
 * Created by jetpeter on 8/4/15.
 *
 * Response model for the imgurService image uplaod request
 */
public class UploadResponse {
    public Image data;
    public int status;
    public boolean success;
}
