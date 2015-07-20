package me.jefferey.screenshotuploader;

import javax.inject.Singleton;

import dagger.Component;
import me.jefferey.screenshotuploader.ui.fragments.LoginFragment;
import me.jefferey.screenshotuploader.utils.UtilsModule;

/**
 * Created by jpetersen on 7/19/15.
 */
@Singleton
@Component(modules = UtilsModule.class)
public interface MainComponent {

    void inject(LoginFragment loginFragment);

}
