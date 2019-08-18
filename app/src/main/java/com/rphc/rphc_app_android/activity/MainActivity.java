package com.rphc.rphc_app_android.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rphc.rphc_app_android.R;
import com.rphc.rphc_app_android.fragment.RemoteSocketFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fragmentContainer, new RemoteSocketFragment(), RemoteSocketFragment.class.getSimpleName());
        transaction.commit();
    }
}
