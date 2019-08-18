package com.rphc.rphc_app_android.activity;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rphc.rphc_app_android.R;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;
import com.rphc.rphc_app_android.rest.RphcRestClient;
import com.rphc.rphc_app_android.rest.RphcService;
import com.rphc.rphc_app_android.rest.model.RemoteSocket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private PreferenceWrapper preferenceWrapper;
    private RphcService rphcService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceWrapper = PreferenceWrapper.getInstance(MainActivity.this);
        rphcService = RphcRestClient.getInstance(MainActivity.this, preferenceWrapper.getCurrentBaseUrl()).getService();

        listRemoteSockets();
    }

    private void listRemoteSockets() {
        final TextView resultView = findViewById(R.id.text);
        Call<List<RemoteSocket>> allSocketsCall = rphcService.getAllRemoteSockets();

        allSocketsCall.enqueue(new Callback<List<RemoteSocket>>() {
            @Override
            public void onResponse(Call<List<RemoteSocket>> call, Response<List<RemoteSocket>> response) {
                List<RemoteSocket> remoteSockets = response.body();

                assert remoteSockets != null;

                resultView.setText("");
                for (RemoteSocket s : remoteSockets) {
                    resultView.append(s.getName());
                }
            }
            @Override
            public void onFailure(Call<List<RemoteSocket>> call, Throwable t) {

            }
        });
    }
}
