package com.rphc.rphc_app_android.rest;


import android.content.Context;

import com.rphc.rphc_app_android.auxiliary.JsonWebToken;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RphcRestClient {

    private static RphcRestClient instance;

    private RphcServiceHolder serviceHolder;

    private RphcRestClient(Context context, String baseUrl) {
        PreferenceWrapper preferenceWrapper = PreferenceWrapper.getInstance(context);

        serviceHolder = new RphcServiceHolder();

        String accessTokenBase64 = null;
        JsonWebToken accessToken = preferenceWrapper.getCurrentAccessToken();
        if (accessToken != null) {
            accessTokenBase64 = accessToken.base64();
        }

        OkHttpClient okHttpClient = new OkHttpClientInstance.Builder(context, serviceHolder)
                .addHeader("Authorization", accessTokenBase64)
                .build();

        RphcService service = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RphcService.class);

        serviceHolder.set(service);
    }

    public RphcService getService() {
        return serviceHolder.get();
    }


    public static RphcRestClient getInstance(Context context, String baseUrl) {
        if (instance == null) {
            instance = new RphcRestClient(context, baseUrl);
        }

        return instance;
    }
}
