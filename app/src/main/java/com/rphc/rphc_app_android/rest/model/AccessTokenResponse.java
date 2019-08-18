package com.rphc.rphc_app_android.rest.model;


import com.google.gson.annotations.SerializedName;


public class AccessTokenResponse {
    @SerializedName("access")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
