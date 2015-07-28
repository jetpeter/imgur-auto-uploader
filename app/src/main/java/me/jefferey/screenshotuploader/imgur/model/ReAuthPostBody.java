package me.jefferey.screenshotuploader.imgur.model;

/**
 * Created by jpetersen on 7/26/15.
 */
public class ReAuthPostBody {
    // As defined in the OAuth2 specification, this field must contain a value of: refresh_token
    public final String grantType = "refresh_token";
    public String clientId;
    public String refreshToken;
    public String clientSecret;
}
