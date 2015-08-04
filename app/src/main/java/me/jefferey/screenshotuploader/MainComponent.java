package me.jefferey.screenshotuploader;

import javax.inject.Singleton;

import dagger.Component;
import me.jefferey.screenshotuploader.imgur.ImgurModule;
import me.jefferey.screenshotuploader.imgur.network.ImageUploadJob;
import me.jefferey.screenshotuploader.ui.MainActivity;
import me.jefferey.screenshotuploader.ui.fragments.ImageListFragment;
import me.jefferey.screenshotuploader.ui.fragments.LoginFragment;
import me.jefferey.screenshotuploader.utils.UtilsModule;

@Singleton
@Component(modules ={UtilsModule.class, ImgurModule.class})
public interface MainComponent {

    void inject(LoginFragment loginFragment);
    void inject(ImageListFragment imageListFragment);
    void inject(MainActivity mainActivity);
    void inject(ImageUploadJob imageUploadJob);

}
