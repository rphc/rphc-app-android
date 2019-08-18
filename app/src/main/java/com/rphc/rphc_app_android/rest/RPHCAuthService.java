package com.rphc.rphc_app_android.rest;


import com.google.gson.JsonObject;
import com.rphc.rphc_app_android.rest.model.AccessTokenResponse;
import com.rphc.rphc_app_android.rest.model.TokenResponse;
import com.rphc.rphc_app_android.rest.model.UserCredentials;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RPHCAuthService {

    @POST("api/auth/token/")
    Call<TokenResponse> obtainTokens(@Body UserCredentials credentials);

    @POST("api/auth/token/refresh/")
    Call<AccessTokenResponse> refreshAccessToken(@Body JsonObject refreshToken);
}
