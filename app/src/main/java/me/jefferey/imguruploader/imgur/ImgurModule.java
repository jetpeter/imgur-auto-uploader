package me.jefferey.imguruploader.imgur;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.jefferey.imguruploader.BuildConfig;
import me.jefferey.imguruploader.imgur.network.ImgurService;
import me.jefferey.imguruploader.imgur.network.ReAuthInterceptor;
import me.jefferey.imguruploader.imgur.network.RequestManager;
import me.jefferey.imguruploader.utils.PreferencesManager;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

@Module
public class ImgurModule {

    public static final String API_URL = "https://api.imgur.com";

    @Provides @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides @Singleton
    RequestInterceptor provideAuthInterceptor(final PreferencesManager preferencesManager) {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String authToken = preferencesManager.getAuthToken();
                if (authToken != null) {
                    request.addHeader("Authorization", "Bearer " + authToken);
                } else {
                    request.addHeader("Authorization", "Client-ID" + BuildConfig.APPLICATION_ID);
                }
            }
        };
    }

    @Provides @Singleton
    ReAuthInterceptor provideReAuthInterceptor(Gson gson, PreferencesManager preferencesManager) {
        return new ReAuthInterceptor(gson, preferencesManager);
    }


    @Provides @Singleton
    OkHttpClient provideHttpClient(ReAuthInterceptor reAuthInterceptor) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(reAuthInterceptor);
        return okHttpClient;
    }

    @Provides @Singleton
    RestAdapter provideRestAdapter(Gson gson, RequestInterceptor authInterceptor, OkHttpClient okHttpClient) {
        return new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(authInterceptor)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    @Provides @Singleton
    ImgurService provideImgurService(RestAdapter restAdapter) {
        return restAdapter.create(ImgurService.class);
    }

    @Provides @Singleton
    RequestManager provideRequestManager(ImgurService imgurService, PreferencesManager preferencesManager, Bus bus) {
        return new RequestManager(imgurService, preferencesManager, bus);
    }
}
