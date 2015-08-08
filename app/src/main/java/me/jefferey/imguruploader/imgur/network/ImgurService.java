package me.jefferey.imguruploader.imgur.network;


import me.jefferey.imguruploader.imgur.model.Gallery;
import me.jefferey.imguruploader.imgur.model.UploadResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by jetpeter on 6/12/15.
 *
 * Api Definitions for Imgur
 */
public interface ImgurService {

    @GET("/3/account/{username}/images/{page}.json")
    void getSubmissions(@Path("username") String username, @Path("page") int page, Callback<Gallery> gallery);

    @Multipart
    @POST("/3/image")
    UploadResponse uploadImage(@Part("image") TypedFile image);

}
