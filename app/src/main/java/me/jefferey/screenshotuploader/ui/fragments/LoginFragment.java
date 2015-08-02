package me.jefferey.screenshotuploader.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jefferey.screenshotuploader.BuildConfig;
import me.jefferey.screenshotuploader.MainComponent;
import me.jefferey.screenshotuploader.R;
import me.jefferey.screenshotuploader.ScreenshotUploaderApplication;
import me.jefferey.screenshotuploader.utils.PreferencesManager;

public class LoginFragment extends Fragment {

    private static final String TAG = "AUTH_WEB_VIEW";
    //https://api.imgur.com/oauth2/authorize?client_id={...}&response_type=token&state=APPLICATION_STATE
    private static final String AUTH_ENDPOINT = "/oauth2/authorize";
    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_RESPONSE_TYPE = "response_type";
    private static final String RESPONSE_TYPE = "token";

    private static final String TOKEN_KEY = "access_token";
    private static final String USERNAME_KEY = "account_username";
    private static final String ACCOUNT_ID_KEY = "account_id";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";

    @Inject
    PreferencesManager mPreferencesManager;

    private LoginFragmentInterface mCallback;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainComponent mainComponent = ScreenshotUploaderApplication.getMainComponent();
        mainComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WebView content = (WebView) inflater.inflate(R.layout.fragment_login, container, false);
        initWebView(content);
        return content;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof  LoginFragmentInterface) {
            mCallback = (LoginFragmentInterface) activity;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getUrl());
        webView.setWebViewClient(mLoginWebViewClient);
    }

    /**
     * @return The auth URL for the login webview
     */
    private String getUrl() {
        return BuildConfig.APP_URL + AUTH_ENDPOINT + "?"
                + PARAM_CLIENT_ID + "=" + BuildConfig.CLIENT_ID + "&"
                + PARAM_RESPONSE_TYPE + "=" + RESPONSE_TYPE;
    }

    private void setResult(Map<String, String> result) {
        String token = result.get(TOKEN_KEY);
        String userId = result.get(ACCOUNT_ID_KEY);
        String username = result.get(USERNAME_KEY);
        String refreshToken = result.get(REFRESH_TOKEN_KEY);
        mPreferencesManager.setLoggedIn(token, refreshToken, username);
        if (mCallback != null) {
            mCallback.onLoginComplete(token, userId, username, refreshToken);
        }

    }

    private final WebViewClient mLoginWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // {BuildConfig.AUTH_REDIRECT_URL}#access_token=ACCESS_TOKEN&token_type=Bearer&expires_in=3600
            if (url.startsWith(BuildConfig.AUTH_REDIRECT_URL)) {
                if (url.contains("?error=")) {
                    Log.v(TAG, "Error: " + url);
                } else {
                    String parameters = url.split("#")[1];
                    Map<String, String> result = splitQueryParameters(parameters);
                    setResult(result);
                }
                return true;
            } else {
                return false;
            }
        }

        /**
         * Given a string of url query parameters split the string into a map.  Duplicate keys
         * are not supported
         */
        public Map<String, String> splitQueryParameters(String query) {
            Map<String, String> parameters = new HashMap<>();
            String[] paramStrings = query.split("&");
            for (String paramString : paramStrings) {
                int idx = paramString.indexOf("=");
                try {
                    parameters.put(URLDecoder.decode(paramString.substring(0, idx), "UTF-8"),
                                   URLDecoder.decode(paramString.substring(idx + 1), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return parameters;
        }
    };

    public interface LoginFragmentInterface {
        void onLoginComplete(String token, String userId, String username, String refreshToken);
    }
}
