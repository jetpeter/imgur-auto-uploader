package me.jefferey.screenshotuploader.imgur.response;

import android.support.annotation.NonNull;

import retrofit.client.Response;

import retrofit.RetrofitError;

/**
 * Created by jpetersen on 7/21/15.
 *
 * Response object of T and some request meta data
 */
public abstract class ResponseWrapper<T> {

    private final String mTag;
    private final boolean mSuccess;
    private final Response mResponse;
    private final T mPayload;
    private final RetrofitError mError;

    public ResponseWrapper(@NonNull String tag, @NonNull Response response, @NonNull T payload, boolean success) {
        mTag = tag;
        mSuccess = success;
        mResponse =  response;
        mPayload = payload;
        mError = null;
    }

    public ResponseWrapper(@NonNull String tag, @NonNull RetrofitError error, boolean success) {
        mTag = tag;
        mSuccess = success;
        mResponse =  error.getResponse();
        mPayload = null;
        mError = error;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public Response getResponse() {
        return mResponse;
    }

    public T getData() {
        return mPayload;
    }

    public RetrofitError getError() {
        return mError;
    }
}
