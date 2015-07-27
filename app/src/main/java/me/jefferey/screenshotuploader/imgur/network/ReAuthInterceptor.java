package me.jefferey.screenshotuploader.imgur.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import me.jefferey.screenshotuploader.utils.PreferencesManager;

/**
 * Created by jpetersen on 7/26/15.
 */
public class ReAuthInterceptor  implements Interceptor {

    private final PreferencesManager mPreferencesManager;

    public ReAuthInterceptor(PreferencesManager preferencesManager) {
        mPreferencesManager = preferencesManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        // try the request
        Response response = chain.proceed(request);

        if (response.code() == 403) {



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

}