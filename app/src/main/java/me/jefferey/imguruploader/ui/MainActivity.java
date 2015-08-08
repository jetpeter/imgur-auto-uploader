package me.jefferey.imguruploader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

import me.jefferey.imguruploader.Constants;
import me.jefferey.imguruploader.R;
import me.jefferey.imguruploader.UploaderApplication;
import me.jefferey.imguruploader.imgur.network.ImageUploadJob;
import me.jefferey.imguruploader.imgur.network.RequestManager;
import me.jefferey.imguruploader.ui.fragments.ImageListFragment;
import me.jefferey.imguruploader.utils.FilePathResolver;
import me.jefferey.imguruploader.utils.PreferencesManager;

public class MainActivity extends AppCompatActivity implements ImageListFragment.Callback {

    private static final String TAG = "MainActivity";

    @Inject RequestManager mRequestManager;
    @Inject PreferencesManager mPreferencesManager;
    @Inject JobManager mJobManager;
    @Inject FilePathResolver mFilePathResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UploaderApplication.getMainComponent().inject(this);
        setContentView(R.layout.activity_main);
        initStartActivities();
    }

    public void initStartActivities() {
        if (!mPreferencesManager.isLoggedIn()) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, Constants.ACTIVITY_RESULT_LOGIN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.ACTIVITY_RESULT_LOGIN:
                onLoginActivityResult(resultCode);
                break;
            case Constants.ACTIVITY_RESULT_GALLERY:
                onGalleryResult(resultCode, data);
                break;
        }
    }

    private void onLoginActivityResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            mRequestManager.getUserSubmissions(TAG, 0);
        } else {
            // We do not support a logged out state so quit the app
            finish();
        }
    }

    public void onGalleryResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data.getData() != null) {
            mJobManager.addJobInBackground(new ImageUploadJob(data.getData()));
        }
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

    @Override
    public void onUploadImageClick() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, Constants.ACTIVITY_RESULT_GALLERY);
    }
}
