package me.jefferey.imguruploader.imgur.model;


/**
 * Created by jetpeter on 6/12/15.
 *
 * Model for imgur image
 */
public class Image {

    private String id;
    private String title;
    private String description;
    private String datetime;
    private String type;
    private boolean animated;
    private int width;
    private int height;
    private long size;
    private long views;
    private long bandwidth;
    private boolean favorite;
    private boolean nsfw;
    private String section;
    private String accountUrl;
    private String accountId;
    private String commentPreview;
    private String topic;
    private int topicId;
    private String gifv;
    private String webm;
    private String mp4;
    private String link;
    private boolean looping;
    private int commentCount;
    private int ups;
    private int downs;
    private int score;
    private boolean isAlbum;

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

    public boolean isAnimated() {
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
        return accountUrl;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCommentPreview() {
        return commentPreview;
    }

    public String getTopic() {
        return topic;
    }

    public int getTopicId() {
        return topicId;
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
        return commentCount;
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
        return isAlbum;
    }
}


