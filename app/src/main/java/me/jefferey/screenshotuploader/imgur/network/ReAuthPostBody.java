package me.jefferey.screenshotuploader.imgur.network;

/**
 * Created by jpetersen on 7/26/15.
 */
public class ReAuthPostBody {
    String accessToken;
    String refreshToken;
    String expiresIn;
    String tokenType;
    String accountUsername;
}
