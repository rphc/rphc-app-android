package com.rphc.rphc_app_android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rphc.rphc_app_android.R;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;
import com.rphc.rphc_app_android.fragment.LedStripFragment;
import com.rphc.rphc_app_android.fragment.RemoteSocketFragment;


public class MainActivity extends AppCompatActivity {


    private PreferenceWrapper preferenceWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceWrapper = PreferenceWrapper.getInstance(MainActivity.this);

        initToolbar();
        initViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;

            case R.id.menu_logout:
                logoutUser();
                return true;

            default:
                return false;
        }
    }


    private void initToolbar() {
        Toolbar actionbar = findViewById(R.id.toolbar);
        setSupportActionBar(actionbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
    }

    private void initViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }


    private void logoutUser() {
        preferenceWrapper.setCurrentRefreshToken(null);
        preferenceWrapper.setCurrentAccessToken(null);

        startActivity(new Intent(MainActivity.this, LauncherActivity.class));
        finish();
    }


    public class MainPagerAdapter extends FragmentPagerAdapter {

        MainPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RemoteSocketFragment();

                case 1:
                    return new LedStripFragment();

                default:
                    return new RemoteSocketFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Sockets";

                case 1:
                    return "Addressable LEDs";

                default:
                    return "Sockets";
            }
        }
    }
}
