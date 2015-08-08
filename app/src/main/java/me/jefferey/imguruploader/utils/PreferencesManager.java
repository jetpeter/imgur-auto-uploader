package me.jefferey.imguruploader.utils;

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
    private static final String LOGGED_IN = "logged_in";

    private SharedPreferences mAppPreferences;

    public PreferencesManager(Context context) {
        mAppPreferences = context.getSharedPreferences(APP_PREFS_FILE, Context.MODE_PRIVATE);
    }

    public void setLoggedIn(String authToken, String refreshToken, String username) {
        SharedPreferences.Editor editor = mAppPreferences.edit();
        editor.putString(AUTH_TOKEN, authToken);
        editor.putString(REFRESH_TOKEN, refreshToken);
        editor.putString(USERNAME, username);
        editor.putBoolean(LOGGED_IN, true);
        editor.apply();
    }

    public void setLoggedOut() {
        SharedPreferences.Editor editor = mAppPreferences.edit();
        editor.putString(AUTH_TOKEN, null);
        editor.putString(REFRESH_TOKEN, null);
        editor.putString(USERNAME, null);
        editor.putBoolean(LOGGED_IN, false);
        editor.apply();
    }

    public String getAuthToken() {
        return mAppPreferences.getString(AUTH_TOKEN, null);
    }

    public String getRefreshToken() {
        return mAppPreferences.getString(REFRESH_TOKEN, null);
    }

    public String getUsername() {
        return mAppPreferences.getString(USERNAME, null);
    }

    public boolean isLoggedIn() {
        return mAppPreferences.getBoolean(LOGGED_IN, false);
    }
}
