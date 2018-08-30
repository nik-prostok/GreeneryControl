package com.example.nik.greenery.Fragments;

import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nik.greenery.BluetoothConnection.BluetoothConnection;
import com.example.nik.greenery.BluetoothConnection.BluetoothService;
import com.example.nik.greenery.Data.RecycleData;
import com.example.nik.greenery.R;
import com.example.nik.greenery.SetupRecyclerViewAdapter.SetupRecyclerViewAdapter;

/**
 * Created by Nik on 29.05.2018.
 */

public class SetupFragment extends Fragment{
    RecyclerView rv;
    SetupRecyclerViewAdapter adapter;

    BluetoothConnection bluetoothConnection;
    BluetoothService bluetoothService;

    boolean syncTime = true;


    public static SetupFragment newInstance(String name) {
        SetupFragment catFragment = new SetupFragment();
        Bundle args = new Bundle();
        args.putString("Name", name);
        // args.putSerializable("BluetoothConnection");
        catFragment.setArguments(args);
        return catFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String name = getArguments().getString("Name", "");
        Log.d("NAMEsetupFragment", name);

        final Handler handlerConnection = new Handler() {
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null){
                    byte[] mmBuffer = (byte[]) msg.obj;
                    String strFromBluetooth = new String(mmBuffer);
                    Log.i("msgFromSetupBluetooth", strFromBluetooth);
                }
            }
        };



        Thread threadConnection = new Thread(new Runnable() {

            @Override
            public void run() {
                BluetoothSocket bluetoothSocket = bluetoothConnection.getBluetoothSocket();
                bluetoothService = new BluetoothService(bluetoothSocket, handlerConnection);
            }
        });
        threadConnection.start();

        final Handler handlerSet = new Handler() {
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null){
                    String strFromSet = (String) msg.obj;
                    Log.i("msgFromSetupSetSettings", strFromSet);
                    bluetoothService.sendMessage(strFromSet);
                }
            }
        };

        adapter = new SetupRecyclerViewAdapter(handlerSet, syncTime);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv = getActivity().findViewById(R.id.setup_rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setup_layout, container, false);
    }

    public void setBluetoothConnection(BluetoothConnection bluetoothConnection){
        this.bluetoothConnection = bluetoothConnection;
    }

    public void setSyncTime(boolean syncTime){
        this.syncTime = syncTime;
    }
}
