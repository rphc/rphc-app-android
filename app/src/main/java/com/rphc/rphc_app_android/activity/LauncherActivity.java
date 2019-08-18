package com.rphc.rphc_app_android.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;


public class LauncherActivity extends AppCompatActivity {

    private PreferenceWrapper preferenceWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceWrapper = PreferenceWrapper.getInstance(LauncherActivity.this);

        if (isUserAuthenticated()) {
            startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
        } else {
            startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
        }

        finish();
    }

    private boolean isUserAuthenticated() {
        return preferenceWrapper.getCurrentAccessToken() != null;
    }
}
