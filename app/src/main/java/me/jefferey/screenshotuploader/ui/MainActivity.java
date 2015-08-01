package me.jefferey.screenshotuploader.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jefferey.screenshotuploader.R;
import me.jefferey.screenshotuploader.ScreenshotUploaderApplication;
import me.jefferey.screenshotuploader.imgur.network.RequestManager;
import me.jefferey.screenshotuploader.imgur.response.ResponseGallery;
import me.jefferey.screenshotuploader.utils.PreferencesManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Inject RequestManager mRequestManager;
    @Inject PreferencesManager mPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenshotUploaderApplication.getMainComponent().inject(this);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(this, LoginActivity.class));
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
