package com.rphc.rphc_app_android.activity;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.rphc.rphc_app_android.R;
import com.rphc.rphc_app_android.auxiliary.JsonWebToken;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;
import com.rphc.rphc_app_android.rest.RPHCAuthClient;
import com.rphc.rphc_app_android.rest.RPHCAuthService;
import com.rphc.rphc_app_android.rest.RphcRestClient;
import com.rphc.rphc_app_android.rest.RphcService;
import com.rphc.rphc_app_android.rest.model.AccessTokenResponse;
import com.rphc.rphc_app_android.rest.model.Message;
import com.rphc.rphc_app_android.rest.model.TokenResponse;
import com.rphc.rphc_app_android.rest.model.UserCredentials;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private PreferenceWrapper preferenceWrapper;
    private RphcService rphcService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceWrapper = PreferenceWrapper.getInstance(LoginActivity.this);

        // TODO get URL (+ PORT), EMAIL & PASSWORD from UI

        rphcService = RphcRestClient.getInstance(LoginActivity.this).getService();

        Call<Message> enableCall = rphcService.enableRemoteSocket(1);
        enableCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.d("TAG", "CODE: " + response.code());
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });

//        Call<TokenResponse> tokenCall = rphcService.obtainTokens("admin@admin.de", "admin");
//
//        tokenCall.enqueue(new Callback<TokenResponse>() {
//            @Override
//            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
//
//                TokenResponse tokenResponse = response.body();
//                JsonWebToken accessToken = JsonWebToken.fromString(tokenResponse.getAccessToken());
//                JsonWebToken refreshToken = JsonWebToken.fromString(tokenResponse.getRefreshToken());
//
//                preferenceWrapper.setCurrentAccessToken(accessToken);
//                preferenceWrapper.setCurrentRefreshToken(refreshToken);
//            }
//            @Override
//            public void onFailure(Call<TokenResponse> call, Throwable t) {
//                Log.e("TAG", t.toString());
//            }
//        });

    }
}
