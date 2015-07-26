package me.jefferey.screenshotuploader.imgur;

import me.jefferey.screenshotuploader.imgur.model.Gallery;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by jetpeter on 6/12/15.
 *
 * Api Definitions for Imgur
 */
public interface ImgurService {

    @GET("/3/account/{username}/images/{page}.json")
    void getSubmissions(@Path("username") String username, @Path("page") int page, Callback<Gallery> gallery);

}
