package com.rphc.rphc_app_android.rest;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RPHCAuthClient {

    private static RPHCAuthClient instance;
    private RPHCAuthService rphcAuthService;

    private RPHCAuthClient(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();

        rphcAuthService = retrofit.create(RPHCAuthService.class);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(baseInterceptor());
        return httpClient.build();
    }

    private Interceptor baseInterceptor() {
        return new Interceptor() {
            @Override
            @NonNull
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body());

                return chain.proceed(builder.build());
            }
        };
    }

    public RPHCAuthService getService() {
        return rphcAuthService;
    }

    public static RPHCAuthClient getInstance(String baseUrl) {
        if (instance == null) {
            instance = new RPHCAuthClient(baseUrl);
        }
        return instance;
    }
}
