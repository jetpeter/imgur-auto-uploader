package me.jefferey.imguruploader;

import android.support.v4.content.LocalBroadcastManager;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import me.jefferey.imguruploader.imgur.ImgurModule;
import me.jefferey.imguruploader.imgur.network.ImageUploadJob;
import me.jefferey.imguruploader.imgur.network.ImgurService;
import me.jefferey.imguruploader.service.PhotoSnooperService;
import me.jefferey.imguruploader.ui.MainActivity;
import me.jefferey.imguruploader.ui.fragments.ImageListFragment;
import me.jefferey.imguruploader.ui.fragments.LoginFragment;
import me.jefferey.imguruploader.utils.FilePathResolver;
import me.jefferey.imguruploader.utils.UtilsModule;

@Singleton
@Component(modules ={UtilsModule.class, ImgurModule.class})
public interface MainComponent {

    void inject(LoginFragment loginFragment);
    void inject(ImageListFragment imageListFragment);
    void inject(MainActivity mainActivity);
    void inject(ImageUploadJob imageUploadJob);
    void inject(PhotoSnooperService photoSnooperService);

    ImgurService provideImgurService();
    FilePathResolver provideFilePathResolver();
    Bus provideBus();
}
