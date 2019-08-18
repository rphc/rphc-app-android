package com.rphc.rphc_app_android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.rphc.rphc_app_android.R;
import com.rphc.rphc_app_android.auxiliary.JsonWebToken;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;
import com.rphc.rphc_app_android.rest.RphcRestClient;
import com.rphc.rphc_app_android.rest.RphcService;
import com.rphc.rphc_app_android.rest.model.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private PreferenceWrapper preferenceWrapper;

    private TextInputLayout baseUrlInputLayout, emailInputLayout, passwordInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceWrapper = PreferenceWrapper.getInstance(LoginActivity.this);

        initViews();
    }

    private void initViews() {
        baseUrlInputLayout = findViewById(R.id.login_base_url);
        emailInputLayout = findViewById(R.id.login_email);
        passwordInputLayout = findViewById(R.id.login_password);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(onLoginClicked());

        // if this is not a first time login, at least the URL can be shown
        String baseUrl = preferenceWrapper.getCurrentBaseUrl();
        if (baseUrl != null) {
            baseUrlInputLayout.getEditText().setText(baseUrl);
        }
    }

    private View.OnClickListener  onLoginClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String baseUrl = baseUrlInputLayout.getEditText().getText().toString();
                String email = emailInputLayout.getEditText().getText().toString();
                String password = passwordInputLayout.getEditText().getText().toString();

                getTokens(baseUrl, email, password);
            }
        };
    }

    private void getTokens(String baseUrl, String email, String password) {
        preferenceWrapper.setCurrentBaseUrl(baseUrlInputLayout.getEditText().getText().toString());

        RphcService rphcService = RphcRestClient.getInstance(LoginActivity.this, baseUrl).getService();
        Call<TokenResponse> tokenCall = rphcService.obtainTokens(email, password);

        tokenCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                TokenResponse tokenResponse = response.body();
                JsonWebToken accessToken = JsonWebToken.fromString(tokenResponse.getAccessToken());
                JsonWebToken refreshToken = JsonWebToken.fromString(tokenResponse.getRefreshToken());

                preferenceWrapper.setCurrentAccessToken(accessToken);
                preferenceWrapper.setCurrentRefreshToken(refreshToken);

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("TAG", t.toString());
                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
