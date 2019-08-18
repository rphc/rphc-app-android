package com.rphc.rphc_app_android.rest;


import android.content.Context;

import com.rphc.rphc_app_android.auxiliary.JsonWebToken;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpClientInstance {
    public static class Builder {

        private HashMap<String, String> headers = new HashMap<>();
        private Context context;
        private RphcServiceHolder serviceHolder;

        private PreferenceWrapper preferenceWrapper;


        public Builder(Context context, RphcServiceHolder serviceHolder) {
            this.context = context;
            this.serviceHolder = serviceHolder;

            preferenceWrapper = PreferenceWrapper.getInstance(context);
        }

        public Builder addHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public OkHttpClient build() {
            TokenAuthenticator tokenAuthenticator = new TokenAuthenticator(context, serviceHolder);

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                                        @Override
                                        public Response intercept(Chain chain) throws IOException {

                                            // default headers
                                            Request.Builder requestBuilder = chain.request().newBuilder()
                                                    .addHeader("Accept", "application/json")
                                                    .addHeader("Content-Type", "application/json");

                                            // additional headers
//                                            Iterator it = headers.entrySet().iterator();

//                                            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                                                if (entry.getKey() != null && entry.getValue() != null) {
//                                                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
//                                                }
//                                            }

                                            JsonWebToken accessToken = preferenceWrapper.getCurrentAccessToken();

                                            if (accessToken != null) {
                                                requestBuilder.addHeader("Authorization", "Bearer " + accessToken.base64());
                                            }

                                            return chain.proceed(requestBuilder.build());
                                        }
                                    }
                    )
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);

            okHttpClientBuilder.authenticator(tokenAuthenticator);
            return okHttpClientBuilder.build();
        }
    }
}
