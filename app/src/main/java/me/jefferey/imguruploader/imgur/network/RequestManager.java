package me.jefferey.imguruploader.imgur.network;

import com.squareup.otto.Bus;

import me.jefferey.imguruploader.imgur.model.Gallery;
import me.jefferey.imguruploader.imgur.response.ResponseGallery;
import me.jefferey.imguruploader.utils.PreferencesManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jpetersen on 7/21/15.
 * Handle Responses from the ImgurApi
 */
public class RequestManager {

    private final ImgurService mImgurService;
    private final PreferencesManager mPreferencesManager;
    private final Bus mBus;

    public RequestManager(ImgurService imgurService, PreferencesManager preferencesManager, Bus bus) {
        mImgurService = imgurService;
        mPreferencesManager = preferencesManager;
        mBus = bus;
    }

    public void getUserSubmissions(final String tag, final int page) {
        mImgurService.getSubmissions(mPreferencesManager.getUsername(), page, new Callback<Gallery>() {
            @Override
            public void success(Gallery gallery, Response response) {
                ResponseGallery responseGallery = new ResponseGallery(tag, response, gallery, gallery.success);
                mBus.post(responseGallery);
            }

            @Override
            public void failure(RetrofitError error) {
                ResponseGallery responseGallery = new ResponseGallery(tag, error, false);
                mBus.post(responseGallery);
            }
        });
    }


}
