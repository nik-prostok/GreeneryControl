package com.example.nik.greenery.Fragments;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nik.greenery.SensorsRecyclerViewAdapter.SensorsRecyclerViewAdapter;
import com.example.nik.greenery.BluetoothConnection.BluetoothConnection;
import com.example.nik.greenery.BluetoothConnection.BluetoothService;
import com.example.nik.greenery.Data.RecycleData;
import com.example.nik.greenery.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nik on 11.05.2018.
 */

public class SensorsFragment extends Fragment {

    RecyclerView rv;
    SensorsRecyclerViewAdapter adapter;
    RecycleData recycleData;
    BluetoothConnection bluetoothConnection;
    BluetoothService bluetoothService;

    private Handler mHandler = new Handler();

    JSONObject data;

    boolean manualFlag = false;
    String manualModeString;

    final Handler handlerManualMode = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                Boolean flag = (Boolean) msg.obj;
                Log.i("manualModeHandler", flag.toString());
                manualFlag = flag;
                if (!manualFlag){
                    JSONObject jsonObjectAutoMode = new JSONObject();
                    try {
                        jsonObjectAutoMode.put("mode", "auto");

                        bluetoothService.sendMessage(new String(jsonObjectAutoMode.toString() + "\n"));
                        Log.d("mode", jsonObjectAutoMode.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    public static SensorsFragment newInstance(String name) {
        SensorsFragment catFragment = new SensorsFragment();
        Bundle args = new Bundle();
        args.putString("Name", name);
        catFragment.setArguments(args);
        return catFragment;
    }

    public void setBluetoothConnection(BluetoothConnection bluetoothConnection) {
        this.bluetoothConnection = bluetoothConnection;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name = getArguments().getString("Name", "");
        Log.d("NAMEsensorsFragment", name);
        Log.d("bluetooth.isConnected()", String.valueOf(bluetoothConnection.isConnected()));

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mode", "manual");
            jsonObject.put("h", "0");
            jsonObject.put("a", "0");
            jsonObject.put("w", "0");
            jsonObject.put("l", "0");
            manualModeString = new String(jsonObject.toString() + '\n');
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Handler handlerSet = new Handler() {
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null) {
                    manualModeString = (String) msg.obj;
                    Log.i("msgFromSetupSet", manualModeString);
                    //bluetoothService.sendMessage(strFromSet);
                }
            }
        };

        final Handler handlerConnection = new Handler() {
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null) {
                    byte[] mmBuffer = (byte[]) msg.obj;
                    String strFrom = new String(mmBuffer);
                    Log.i("msgFromHandler", strFrom);
                    try {
                        data = new JSONObject(strFrom);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        String temp = data.getString("temp");
                        String hum = data.getString("hum");
                        String lux = data.getString("lux");
                        String gnd = data.getString("gnd");
                        boolean heat = data.getBoolean("heat");
                        boolean aeration = data.getBoolean("aeration");
                        boolean light = data.getBoolean("light");
                        boolean watering = data.getBoolean("watering");
                        Log.d("temp", temp);
                        Log.d("hum", hum);
                        Log.d("lux", lux);
                        Log.d("gnd", gnd);
                        Log.d("heat", String.valueOf(heat));
                        Log.d("aeration", String.valueOf(aeration));
                        Log.d("light", String.valueOf(light));
                        Log.d("watering", String.valueOf(watering));
                        UpdateData(temp, hum, gnd, lux, heat, aeration, light, watering);
                        Log.d("flagManual", String.valueOf(manualFlag));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        BluetoothSocket bluetoothSocket = bluetoothConnection.getBluetoothSocket();
        bluetoothService = new BluetoothService(bluetoothSocket, handlerConnection);


        /*Thread threadConnection = new Thread(new Runnable() {

            @Override
            public void run() {
                BluetoothSocket bluetoothSocket = bluetoothConnection.getBluetoothSocket();
                bluetoothService = new BluetoothService(bluetoothSocket, handlerConnection);

                if (bluetoothSocket.isConnected()) {
                    JSONObject getDataRequest = new JSONObject();
                    try {
                        getDataRequest.put("mode", "getData");
                        String message = new String(getDataRequest.toString() + '\n');
                        bluetoothService.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("mode", getDataRequest.toString());
                }
            }
        });
        threadConnection.start();*/

        mHandler.removeCallbacks(timeUpdaterRunnable);
        // Добавляем Runnable-объект timeUpdaterRunnable в очередь
        // сообщений, объект должен быть запущен после задержки в 100 мс
        mHandler.postDelayed(timeUpdaterRunnable, 100);

        recycleData = new RecycleData("N/A", "N/A", "N/A", "N/A", false, false, false, false);
        adapter = new SensorsRecyclerViewAdapter(recycleData, handlerSet);

    }


    // Описание Runnable-объекта
    private Runnable timeUpdaterRunnable = new Runnable() {
        public void run() {
            BluetoothSocket bluetoothSocket = bluetoothConnection.getBluetoothSocket();
            if (bluetoothSocket.isConnected()) {
                JSONObject request = new JSONObject();
                if (manualFlag){
                    Log.d("Runnable", manualModeString);
                    bluetoothService.sendMessage(manualModeString);
                } else {
                    try {
                        request.put("mode", "getData");
                        String message = new String(request.toString() + '\n');
                        bluetoothService.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
             //   }
                Log.d("mode", request.toString());
                mHandler.postDelayed(this, 3000);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv = getActivity().findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sensors_layout, container, false);
    }

    public void UpdateData(String temp, String humAir, String humGround, String light, boolean heat, boolean aeration, boolean lighting, boolean watering) {
        adapter.setManualMode(manualFlag);
        adapter.dataChanged(new RecycleData(temp, humAir, humGround, light, heat, aeration, lighting, watering));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Добавляем Runnable-объект
        mHandler.postDelayed(timeUpdaterRunnable, 100);
    }

    @Override
    public void onPause() {
        // Удаляем Runnable-объект для прекращения задачи
        mHandler.removeCallbacks(timeUpdaterRunnable);
        super.onPause();
    }

    public void setManualMode(boolean manualFlag) {
        this.manualFlag = manualFlag;
    }

    public Handler getHandlerManualMode() {
        return handlerManualMode;
    }
}
