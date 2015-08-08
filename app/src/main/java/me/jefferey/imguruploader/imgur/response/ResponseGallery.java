package me.jefferey.imguruploader.imgur.response;

import android.support.annotation.NonNull;

import retrofit.client.Response;

import me.jefferey.imguruploader.imgur.model.Gallery;
import retrofit.RetrofitError;

/**
 * Created by jpetersen on 7/21/15.
 *
 * Response class for gallery objects to be posted to the message bus
 */
public class ResponseGallery extends ResponseWrapper<Gallery> {
    public ResponseGallery(@NonNull String tag, @NonNull Response response, @NonNull Gallery payload, boolean success) {
        super(tag, response, payload, success);
    }

    public ResponseGallery(@NonNull String tag, @NonNull RetrofitError error, boolean success) {
        super(tag, error, success);
    }
}
