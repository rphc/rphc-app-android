package com.rphc.rphc_app_android.rest.model;


import com.google.gson.annotations.SerializedName;


public class TokenResponse {

    @SerializedName("refresh")
    private String refreshToken;

    @SerializedName("access")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
