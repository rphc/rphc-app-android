package com.rphc.rphc_app_android.rest;


import android.content.Context;

import androidx.annotation.NonNull;

import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RphcRestClient {

    private static RphcRestClient instance;
    private static String BASE_URL = "http://10.0.2.2:8000";

    private PreferenceWrapper preferenceWrapper;
    private RphcService rphcService;
    private RphcServiceHolder serviceHolder;

    private RphcRestClient(Context context) {
        preferenceWrapper = PreferenceWrapper.getInstance(context);

        serviceHolder = new RphcServiceHolder();

        OkHttpClient okHttpClient = new OkHttpClientInstance.Builder(context, serviceHolder)
                .addHeader("Authorization", preferenceWrapper.getCurrentAccessToken().base64())
                .build();

        RphcService service = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RphcService.class);

        serviceHolder.set(service);
    }

    public RphcService getService() {
        return serviceHolder.get();
    }


    public static RphcRestClient getInstance(Context context) {
        if (instance == null) {
            instance = new RphcRestClient(context);
        }

        return instance;
    }
}
