package me.jefferey.screenshotuploader.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import me.jefferey.screenshotuploader.R;
import me.jefferey.screenshotuploader.ScreenshotUploaderApplication;
import me.jefferey.screenshotuploader.imgur.Gallery;
import me.jefferey.screenshotuploader.imgur.ImgurService;
import me.jefferey.screenshotuploader.utils.PreferencesManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    @Inject
    ImgurService mImgurService;
    @Inject
    PreferencesManager mPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenshotUploaderApplication.getMainComponent().inject(this);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(this, LoginActivity.class));

        mImgurService.getSubmissions(mPreferencesManager.getUsername(), 0, new Callback<Gallery>() {
            @Override
            public void success(Gallery gallery, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
