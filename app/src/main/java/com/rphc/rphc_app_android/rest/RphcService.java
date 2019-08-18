package com.rphc.rphc_app_android.rest;


import com.rphc.rphc_app_android.rest.model.AccessTokenResponse;
import com.rphc.rphc_app_android.rest.model.Message;
import com.rphc.rphc_app_android.rest.model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RphcService {

    @POST("api/auth/token/")
    @FormUrlEncoded
    Call<TokenResponse> obtainTokens(@Field("email") String email, @Field("password") String password);

    @POST("api/auth/token/refresh/")
    @FormUrlEncoded
    Call<AccessTokenResponse> refreshAccessToken(@Field("refresh") String refreshToken);

    @POST("api/devices/remote-socket/{id}/enable/")
    Call<Message> enableRemoteSocket(@Path("id") int socketId);

    @POST("api/devices/remote-socket/{id}/disable/")
    Call<Message> disableRemoteSocket(@Path("id") int socketId);
}