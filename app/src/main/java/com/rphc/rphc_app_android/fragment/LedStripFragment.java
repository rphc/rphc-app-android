package com.rphc.rphc_app_android.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;
import com.rphc.rphc_app_android.R;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;
import com.rphc.rphc_app_android.rest.RphcRestClient;
import com.rphc.rphc_app_android.rest.RphcService;
import com.rphc.rphc_app_android.rest.model.AddressableLedStrip;
import com.rphc.rphc_app_android.rest.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LedStripFragment extends Fragment {

    private PreferenceWrapper preferenceWrapper;
    private RphcService rphcService;

    private int ledAddress = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addressable_led_strip, container, false);

        HSLColorPicker colorPicker = v.findViewById(R.id.hlsColorPicker);

        colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelectionEnd(int color) {

                int alpha = (color >> 24) & (0xFF);
                int r = (color >> 16) & (0xFF);
                int g = (color >> 8) & (0xFF);
                int b = color & (0xFF);

//                Log.d("TAG", "Color: " + r + ", " + g + ", " + b + ", " + alpha);

                Call<Message> setColorCall = rphcService.setAddressableLedColor(ledAddress, r, g, b);

                setColorCall.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {

                    }
                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {

                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        preferenceWrapper = PreferenceWrapper.getInstance(getContext());
        rphcService = RphcRestClient.getInstance(getContext(), preferenceWrapper.getCurrentBaseUrl()).getService();

        loadLedStrip();
    }

    private void loadLedStrip() {
        Call<List<AddressableLedStrip>> ledCall = rphcService.getAllAddressableLedStrips();
        ledCall.enqueue(new Callback<List<AddressableLedStrip>>() {
            @Override
            public void onResponse(Call<List<AddressableLedStrip>> call, Response<List<AddressableLedStrip>> response) {
                List<AddressableLedStrip> ledStrips = response.body();

                if (ledStrips.size() > 0) {
                    ledAddress = ledStrips.get(0).getId();
                }
            }

            @Override
            public void onFailure(Call<List<AddressableLedStrip>> call, Throwable t) {

            }
        });
    }
}
