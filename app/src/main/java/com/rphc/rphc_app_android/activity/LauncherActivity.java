package com.rphc.rphc_app_android.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rphc.rphc_app_android.auxiliary.JsonWebToken;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;


public class LauncherActivity extends AppCompatActivity {

    private PreferenceWrapper preferenceWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceWrapper = PreferenceWrapper.getInstance(LauncherActivity.this);

        if (needsAuthentication()) {
            startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
        } else {
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        }

        finish();
    }

    private boolean needsAuthentication() {
        JsonWebToken accessToken = preferenceWrapper.getCurrentAccessToken();
        JsonWebToken refreshToken = preferenceWrapper.getCurrentRefreshToken();

        if (accessToken == null || refreshToken == null) {
            return true;
        }

        return refreshToken.isExpired();
    }
}
