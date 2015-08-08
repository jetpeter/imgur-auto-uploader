package me.jefferey.imguruploader.imgur.network;

import com.google.gson.Gson;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import me.jefferey.imguruploader.imgur.ImgurModule;
import me.jefferey.imguruploader.imgur.model.ReAuthPostBody;
import me.jefferey.imguruploader.imgur.model.RefreshToken;
import me.jefferey.imguruploader.utils.PreferencesManager;

/**
 * Created by jpetersen on 7/26/15.
 *
 * Interceptor that will attempt to refresh the token for any failed request made with ImgurService
 */
public class ReAuthInterceptor  implements Interceptor {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String RE_AUTH_URL = ImgurModule.API_URL + "/oauth2/token";

    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    private final PreferencesManager mPreferencesManager;
    private final Gson mGson;

    public ReAuthInterceptor(Gson gson, PreferencesManager preferencesManager) {
        mPreferencesManager = preferencesManager;
        mGson = gson;
    }

    @Override
    public Response intercept(Chain chain) throws IOException{
        Request request = chain.request();
        // try the request
        Response response = chain.proceed(request);
        if (response.code() == 403 && refreshAuthToken()) {
            // create a new request and modify it accordingly using the new token
            String authToken = mPreferencesManager.getAuthToken();
            if (authToken != null) {
                Request newRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer " + authToken)
                    .build();
                return chain.proceed(newRequest);
            }
        }
        // otherwise just pass the original response on
        return response;
    }

    private boolean refreshAuthToken() {
        ReAuthPostBody postData = new ReAuthPostBody();
        postData.refreshToken = mPreferencesManager.getRefreshToken();
        RequestBody body = RequestBody.create(JSON, mGson.toJson(postData));
        Request reAuthRequest = new Request.Builder()
                .url(RE_AUTH_URL)
                .post(body)
                .build();
        try {
            Response reAuthResponse = mOkHttpClient.newCall(reAuthRequest).execute();
            if (reAuthResponse.isSuccessful()) {
                RefreshToken refreshToken = mGson.fromJson(reAuthResponse.body().string(), RefreshToken.class);
                mPreferencesManager.setLoggedIn(refreshToken.accessToken, refreshToken.refreshToken, refreshToken.accountUsername);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPreferencesManager.setLoggedOut();
        return false;
    }

}