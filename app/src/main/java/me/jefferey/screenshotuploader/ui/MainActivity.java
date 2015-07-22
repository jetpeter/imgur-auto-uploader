package me.jefferey.screenshotuploader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import me.jefferey.screenshotuploader.R;
import me.jefferey.screenshotuploader.ScreenshotUploaderApplication;
import me.jefferey.screenshotuploader.imgur.RequestManager;
import me.jefferey.screenshotuploader.imgur.response.ResponseGallery;
import me.jefferey.screenshotuploader.utils.PreferencesManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Inject
    RequestManager mRequestManager;

    @Inject
    PreferencesManager mPreferencesManager;

    @Inject
    Bus mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenshotUploaderApplication.getMainComponent().inject(this);
        mBus.register(this);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, LoginActivity.class));
        mRequestManager.getUserSubmissions(TAG, 0);
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

    @Subscribe
    public void onSubmissionsReceived(ResponseGallery responseGallery) {
        if (responseGallery.getSuccess()) {
            Toast.makeText(this, "Request Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Request Failed", Toast.LENGTH_LONG).show();
        }
    }

}
