package com.rphc.rphc_app_android.rest;


import android.content.Context;

import androidx.annotation.Nullable;

import com.rphc.rphc_app_android.auxiliary.JsonWebToken;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;
import com.rphc.rphc_app_android.rest.model.AccessTokenResponse;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


public class TokenAuthenticator implements Authenticator {

    private PreferenceWrapper preferenceWrapper;
    private RphcServiceHolder serviceHolder;

    public TokenAuthenticator(Context context, RphcServiceHolder serviceHolder) {
        this.serviceHolder = serviceHolder;
        preferenceWrapper = PreferenceWrapper.getInstance(context);
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) throws IOException {
        if (serviceHolder == null) {
            return null;
        }

        JsonWebToken refreshToken = preferenceWrapper.getCurrentRefreshToken();

        if (refreshToken.isExpired()) {
            return null;
        }

        retrofit2.Response<AccessTokenResponse> refreshTokenResponse = serviceHolder.get().refreshAccessToken(refreshToken.base64()).execute();
        String newAccessToken = refreshTokenResponse.body().getAccessToken();
        preferenceWrapper.setCurrentAccessToken(JsonWebToken.fromString(newAccessToken));

        return response.request().newBuilder()
                .header("Authorization", "Bearer " + newAccessToken)
                .build();

    }
}
