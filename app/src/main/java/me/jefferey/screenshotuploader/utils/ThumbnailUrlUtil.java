package me.jefferey.screenshotuploader.utils;

import me.jefferey.screenshotuploader.imgur.model.Image;

/**
 * Created by jpetersen on 7/26/15.
 */
public class ThumbnailUrlUtil {

    public static final String BASE_IMAGE_ADDRESS = "http://i.imgur.com/";

    public static final char SMALL_SQUARE = 's';
    public static final char BIG_SQUARE = 'b';
    public static final char SMALL_THUMBNAIL = 't';
    public static final char MEDIUM_THUMBNAIL = 'm';
    public static final char LARGE_THUMBNAIL = 'l';
    public static final char HUGE_THUMBNAIL = 'h';

    public static String getThumbnailUrl(String imageId, char size) {
        return BASE_IMAGE_ADDRESS + imageId + size + ".jpg";
    }

    public static String getThumbnailUrl(Image image, char size) {
        return BASE_IMAGE_ADDRESS + image.getId() + size + ".jpg";
    }

}
