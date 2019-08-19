package com.rphc.rphc_app_android.rest;


import com.rphc.rphc_app_android.rest.model.AccessTokenResponse;
import com.rphc.rphc_app_android.rest.model.AddressableLedStrip;
import com.rphc.rphc_app_android.rest.model.Message;
import com.rphc.rphc_app_android.rest.model.RemoteSocket;
import com.rphc.rphc_app_android.rest.model.TokenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RphcService {

    // AUTHENTICATION

    @POST("api/auth/token/")
    @FormUrlEncoded
    Call<TokenResponse> obtainTokens(@Field("email") String email, @Field("password") String password);

    @POST("api/auth/token/refresh/")
    @FormUrlEncoded
    Call<AccessTokenResponse> refreshAccessToken(@Field("refresh") String refreshToken);

    // SOCKETS

    @GET("api/devices/remote-socket/")
    Call<List<RemoteSocket>> getAllRemoteSockets();

    @GET("api/devices/remote-socket/{id}/")
    Call<RemoteSocket> getRemoteSocketDetail(@Path("id") int id);

    @POST("api/devices/remote-socket/{id}/enable/")
    Call<Message> enableRemoteSocket(@Path("id") int socketId);

    @POST("api/devices/remote-socket/{id}/disable/")
    Call<Message> disableRemoteSocket(@Path("id") int socketId);


    // ADDRESSABLE LED STRIPS

    @GET("api/devices/addressable-led-strip/")
    Call<List<AddressableLedStrip>> getAllAddressableLedStrips();

    @POST("api/devices/addressable-led-strip/{id}/set-color/")
    @FormUrlEncoded
    Call<Message> setAddressableLedColor(@Path("id") int addressableLedId, @Field("r") int r, @Field("g") int g, @Field("b") int b);

}
