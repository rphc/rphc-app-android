package com.rphc.rphc_app_android.rest;


import androidx.annotation.Nullable;


public class RphcServiceHolder {
    RphcService service = null;

    @Nullable
    public  RphcService get() {
        return service;
    }

    public void set(RphcService service) {
        this.service = service;
    }
}
