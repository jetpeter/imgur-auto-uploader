package me.jefferey.imguruploader.imgur.model;

import me.jefferey.imguruploader.BuildConfig;

/**
 * Created by jpetersen on 7/26/15.
 * Post body for the imgur api token refresh endpoint
 */
public class ReAuthPostBody {
    // As defined in the OAuth2 specification, this field must contain a value of: refresh_token
    public final String grantType = "refresh_token";
    public final String clientSecret = BuildConfig.IMGUR_API_SECRET;
    public final String clientId = BuildConfig.CLIENT_ID;
    public String refreshToken;
}
