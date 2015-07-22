package me.jefferey.screenshotuploader.imgur.model;

/**
 * Created by jetpeter on 6/12/15.
 *
 * Model for imgur image
 */
@SuppressWarnings("unused")
public class Image {

    public static final String BASE_IMAGE_ADDRESS = "http://i.imgur.com/";

    public static final char SMALL_SQUARE = 's';
    public static final char BIG_SQUARE = 'b';
    public static final char SMALL_THUMBNAIL = 't';
    public static final char MEDIUM_THUMBNAIL = 'm';
    public static final char LARGE_THUMBNAIL = 'l';
    public static final char HUGE_THUMBNAIL = 'h';

    private String id;
    private String title;
    private String description;
    private String datetime;
    private String type;
    private String animated;
    private int width;
    private int height;
    private long size;
    private long views;
    private long bandwidth;
    private boolean favorite;
    private boolean nsfw;
    private String section;
    private String account_url;
    private String account_id;
    private String comment_preview;
    private String topic;
    private int topic_id;
    private String gifv;
    private String webm;
    private String mp4;
    private String link;
    private boolean looping;
    private int comment_count;
    private int ups;
    private int downs;
    private int score;
    private boolean is_album;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getType() {
        return type;
    }

    public String getAnimated() {
        return animated;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getSize() {
        return size;
    }

    public long getViews() {
        return views;
    }

    public long getBandwidth() {
        return bandwidth;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public String getSection() {
        return section;
    }

    public String getAccountUrl() {
        return account_url;
    }

    public String getAccountId() {
        return account_id;
    }

    public String getCommentPreview() {
        return comment_preview;
    }

    public String getTopic() {
        return topic;
    }

    public int getTopicId() {
        return topic_id;
    }

    public String getGifv() {
        return gifv;
    }

    public String getWebm() {
        return webm;
    }

    public String getMp4() {
        return mp4;
    }

    public String getLink() {
        return link;
    }

    public boolean isLooping() {
        return looping;
    }

    public int getCommentCount() {
        return comment_count;
    }

    public int getUps() {
        return ups;
    }

    public int getDowns() {
        return downs;
    }

    public int getScore() {
        return score;
    }

    public boolean isAlbum() {
        return is_album;
    }

    public String getThumbnailSize(char size) {
        return BASE_IMAGE_ADDRESS + id + size + ".jpg";
    }
}


