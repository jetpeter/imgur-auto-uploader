package me.jefferey.screenshotuploader.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.jefferey.screenshotuploader.R;
import me.jefferey.screenshotuploader.ui.fragments.LoginFragment;

/**
 * Created by jpetersen on 7/19/15.
 */
public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentInterface {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onLoginComplete(String token, String userId, String username, String refreshToken) {
        setResult(RESULT_OK);
        finish();
    }
}