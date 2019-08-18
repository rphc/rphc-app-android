package com.rphc.rphc_app_android.rest.model;


import com.google.gson.annotations.SerializedName;


public class Message {
    @SerializedName("msg")
    private String message;

    public String getMessage() {
        return message;
    }
}
