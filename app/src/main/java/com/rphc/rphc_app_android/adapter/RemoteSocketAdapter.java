package com.rphc.rphc_app_android.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rphc.rphc_app_android.R;
import com.rphc.rphc_app_android.rest.model.RemoteSocket;

import java.util.List;


public class RemoteSocketAdapter extends RecyclerView.Adapter<RemoteSocketAdapter.ViewHolder> {


    private List<RemoteSocket> remoteSocketList;
    private OnRemoteSocketClickListener onRemoteSocketClickListener;


    public RemoteSocketAdapter(List<RemoteSocket> remoteSocketList) {
        this.remoteSocketList = remoteSocketList;
    }

    public void addOnRemoteSocketClickListener(OnRemoteSocketClickListener listener) {
        onRemoteSocketClickListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_remote_socket, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RemoteSocket socket = remoteSocketList.get(position);
        holder.socketName.setText(socket.getName());
    }

    @Override
    public int getItemCount() {
        return remoteSocketList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView socketName;
        Button socketButtonOn, socketButtonOff;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            socketName = itemView.findViewById(R.id.remote_socket_name);
            socketButtonOn = itemView.findViewById(R.id.remote_socket_on);
            socketButtonOff = itemView.findViewById(R.id.remote_socket_off);

            socketButtonOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "Click enable in RemoteSocketAdapter");
                    onRemoteSocketClickListener.onClick(remoteSocketList.get(getAdapterPosition()).getId(), true);
                }
            });

            socketButtonOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRemoteSocketClickListener.onClick(remoteSocketList.get(getAdapterPosition()).getId(), false);
                }
            });
        }
    }

    public interface OnRemoteSocketClickListener {
        void onClick(int socketId, boolean enable);
    }
}
