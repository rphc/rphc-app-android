package com.rphc.rphc_app_android.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.rphc.rphc_app_android.R;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;
import com.rphc.rphc_app_android.rest.RphcRestClient;
import com.rphc.rphc_app_android.rest.RphcService;
import com.rphc.rphc_app_android.rest.model.RemoteSocket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemoteSocketFragment extends Fragment {

    private PreferenceWrapper preferenceWrapper;
    private RphcService rphcService;

    private TextView resultView;

    public RemoteSocketFragment() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remote_socket, container, false);

        resultView = v.findViewById(R.id.resultText);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        preferenceWrapper = PreferenceWrapper.getInstance(getContext());
        rphcService = RphcRestClient.getInstance(getContext(), preferenceWrapper.getCurrentBaseUrl()).getService();

        listRemoteSockets();
    }
    private void listRemoteSockets() {
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
                Log.e("TAG", t.toString());
            }
        });
    }

}
