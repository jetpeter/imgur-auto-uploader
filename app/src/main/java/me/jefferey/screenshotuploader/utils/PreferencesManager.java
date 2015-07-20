package me.jefferey.screenshotuploader.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jpetersen on 7/19/15.
 */
public class PreferencesManager {

    private static final String APP_PREFS_FILE = "app_prefs";
    private static final String AUTH_TOKEN = "auth_token";
    private static final String USERNAME = "username";
    private static final String REFRESH_TOKEN = "refresh_token";

    private SharedPreferences mAppPreferences;

    public PreferencesManager(Context context) {
        mAppPreferences = context.getSharedPreferences(APP_PREFS_FILE, Context.MODE_PRIVATE);
    }

    public void setAuthToken(String token) {
        SharedPreferences.Editor editor = mAppPreferences.edit();
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return mAppPreferences.getString(AUTH_TOKEN, null);
    }

    public void setRefreshToken(String token) {
        SharedPreferences.Editor editor = mAppPreferences.edit();
        editor.putString(REFRESH_TOKEN, token);
        editor.apply();
    }

    public String getRefreshToken() {
        return mAppPreferences.getString(REFRESH_TOKEN, null);
    }

    public void setUsername(String username) {
        SharedPreferences.Editor editor = mAppPreferences.edit();
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return mAppPreferences.getString(USERNAME, null);
    }
}
