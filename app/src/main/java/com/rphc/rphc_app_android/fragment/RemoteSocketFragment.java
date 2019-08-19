package com.rphc.rphc_app_android.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rphc.rphc_app_android.R;
import com.rphc.rphc_app_android.adapter.RemoteSocketAdapter;
import com.rphc.rphc_app_android.auxiliary.PreferenceWrapper;
import com.rphc.rphc_app_android.rest.RphcRestClient;
import com.rphc.rphc_app_android.rest.RphcService;
import com.rphc.rphc_app_android.rest.model.Message;
import com.rphc.rphc_app_android.rest.model.RemoteSocket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemoteSocketFragment extends Fragment {

    private PreferenceWrapper preferenceWrapper;
    private RphcService rphcService;

    private RecyclerView recyclerView;

    public RemoteSocketFragment() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remote_socket, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        preferenceWrapper = PreferenceWrapper.getInstance(getContext());
        rphcService = RphcRestClient.getInstance(getContext(), preferenceWrapper.getCurrentBaseUrl()).getService();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listRemoteSockets();
    }

    private void listRemoteSockets() {
        Call<List<RemoteSocket>> allSocketsCall = rphcService.getAllRemoteSockets();

        allSocketsCall.enqueue(new Callback<List<RemoteSocket>>() {
            @Override
            public void onResponse(Call<List<RemoteSocket>> call, Response<List<RemoteSocket>> response) {
                List<RemoteSocket> remoteSockets = response.body();

                Log.d("TAG", "Socket count: " + remoteSockets.size());

                RemoteSocketAdapter socketAdapter = new RemoteSocketAdapter(remoteSockets);

                socketAdapter.addOnRemoteSocketClickListener(new RemoteSocketAdapter.OnRemoteSocketClickListener() {
                    @Override
                    public void onClick(int socketId, boolean enable) {
                        if (enable) {
                            Call<Message> enableSocketCall = rphcService.enableRemoteSocket(socketId);
                            enableSocketCall.enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {

                                }
                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {

                                }
                            });
                        } else {
                            Call<Message> disableSocketCall = rphcService.disableRemoteSocket(socketId);
                            disableSocketCall.enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {

                                }
                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {

                                }
                            });
                        }
                    }
                });

                recyclerView.setAdapter(socketAdapter);
            }

            @Override
            public void onFailure(Call<List<RemoteSocket>> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });
    }

}
