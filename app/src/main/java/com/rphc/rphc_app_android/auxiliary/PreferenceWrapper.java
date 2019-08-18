package com.rphc.rphc_app_android.auxiliary;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.rphc.rphc_app_android.R;


public class PreferenceWrapper {

    private static final String BASE_URL = "BASE_URL";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    private static PreferenceWrapper instance;

    private SharedPreferences preferences;

    // cache
    private String cachedBaseUrl;
    private JsonWebToken cachedAccessToken, cachedRefreshToken;


    private PreferenceWrapper(Context context) {
        preferences = context.getSharedPreferences(context.getString(R.string.user_credentials_file_key), Context.MODE_PRIVATE);
    }


    public String getCurrentBaseUrl() {
        if (cachedBaseUrl == null) {
            String baseUrlString = preferences.getString(BASE_URL, null);
            cachedBaseUrl = baseUrlString;
        }

        return cachedBaseUrl;
    }

    public void setCurrentBaseUrl(String baseUrl) {
        cachedBaseUrl = baseUrl;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(BASE_URL, baseUrl);
        editor.apply();
    }


    public JsonWebToken getCurrentAccessToken() {
        if (cachedAccessToken == null) {
            String tokenString = preferences.getString(ACCESS_TOKEN, null);
            cachedAccessToken = new Gson().fromJson(tokenString, JsonWebToken.class);
        }

        return cachedAccessToken;
    }

    public void setCurrentAccessToken(JsonWebToken token) {
        cachedAccessToken = token;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ACCESS_TOKEN, new Gson().toJson(token));
        editor.apply();
    }


    public JsonWebToken getCurrentRefreshToken() {
        if (cachedRefreshToken == null) {
            String tokenString = preferences.getString(REFRESH_TOKEN, null);
            cachedRefreshToken = new Gson().fromJson(tokenString, JsonWebToken.class);
        }

        return cachedRefreshToken;
    }

    public void setCurrentRefreshToken(JsonWebToken token) {
        cachedRefreshToken = token;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(REFRESH_TOKEN, new Gson().toJson(token));
        editor.apply();
    }


    public static PreferenceWrapper getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceWrapper(context);
        }

        return instance;
    }
}
