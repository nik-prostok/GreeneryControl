package com.example.nik.greenery.BluetoothRecycleViewAdapter.NameDeviceList;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nik.greenery.BluetoothConnection.BluetoothConnection;
import com.example.nik.greenery.Data.BluetoothDeviceInfo;
import com.example.nik.greenery.R;

import java.util.ArrayList;

/**
 * Created by Nik on 30.04.2018.
 */

public class NameDeviceListRecyclerViewAdapter extends RecyclerView.Adapter<NameDeviceListRecyclerViewAdapter.MyViewHolder> {


    // The items to display in your RecyclerView
    private ArrayList<BluetoothDeviceInfo> item;

    private String nameDevice;

    private BluetoothConnection bluetoothConnection;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NameDeviceListRecyclerViewAdapter(ArrayList<BluetoothDeviceInfo> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.name_device, parent, false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    public BluetoothConnection getBluetoothConnection() {
        return bluetoothConnection;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.name_bluetooth_device);
        }
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.mTextView.setText(item.get(position).getNameDevice());
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Text", (String) holder.mTextView.getText());
                nameDevice = (String) holder.mTextView.getText();

                final Handler handlerConnection = new Handler() {
                    public void handleMessage(android.os.Message msg) {
                        super.handleMessage(msg);
                            if (msg.what == 1){
                                holder.mTextView.setTextColor(Color.parseColor("#4a70ad"));
                                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                                holder.mTextView.setTypeface(boldTypeface);
                            } else {
                                holder.mTextView.setTextColor(Color.parseColor("#000000"));
                            }
                        }
                };

                Thread threadConnection = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        bluetoothConnection = new BluetoothConnection(holder.mTextView, nameDevice);
                        bluetoothConnection.connectDevice();
                        if (bluetoothConnection.isConnected()){
                            Log.d("StatusConnection", "Connect");
                            handlerConnection.sendEmptyMessage(1);
                            //bluetoothConnection.closeSocket();
                        } else {
                            handlerConnection.sendEmptyMessage(0);
                            Log.d("StatusConnection", "NO connect");
                        }

                    }
                });
                threadConnection.start();
            }
        };

        holder.mTextView.setOnClickListener(onClickListener);
    }


    @Override
    public int getItemCount() {

        Log.d("size", String.valueOf(item.size()));
        return item.size();
    }

    public void dataChanged(ArrayList<BluetoothDeviceInfo> item) {
        this.item = item;
        notifyDataSetChanged();
    }

    public String getName() {
        return nameDevice;
    }
}
