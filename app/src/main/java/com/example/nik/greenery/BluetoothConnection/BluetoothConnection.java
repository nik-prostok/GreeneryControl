package com.example.nik.greenery.BluetoothConnection;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.nik.greenery.R;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;


/**
 * Created by Nik on 21.05.2018.
 */

public class BluetoothConnection {

    private BluetoothSocket clientSocket;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    String targetNameDevice;
    TextView view;

    boolean notification;

    private Handler mHandler;

    public BluetoothConnection(TextView view, String name) {
        notification = true;
        this.targetNameDevice = name;
        this.view = view;
    }

    public BluetoothConnection(String name) {
        notification = false;
        this.targetNameDevice = name;
    }

    public BluetoothConnection() {

    }

  /*  public void run() {
        connectDevice();
    }*/

    public Set<BluetoothDevice> getNearbyDevices() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        return pairedDevices;
    }

    public BluetoothSocket connectDevice() {

        if (targetNameDevice != null) {
            String mac = getNameForMAC(targetNameDevice, getNearbyDevices());
            Log.d("MAC", mac);
            if (mac != null) {
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(mac);
                Method m = null;
                if (notification)
                    Snackbar.make(view, new String("Подключение к " + targetNameDevice), Snackbar.LENGTH_LONG).show();
                try {
                    m = device.getClass().getMethod(
                            "createRfcommSocket", int.class);
                    clientSocket = (BluetoothSocket) m.invoke(device, 1);
                    clientSocket.connect();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    if (notification)
                        Snackbar.make(view, new String("Нет подключения к " + targetNameDevice), Snackbar.LENGTH_LONG).show();
                }
            } else return null;
        } else return null;

        if (isConnected()) {
            if (notification)
                Snackbar.make(view, new String("Подключено к " + targetNameDevice), Snackbar.LENGTH_LONG).show();
             //   mHandler.sendEmptyMessage(1);
            return clientSocket;
        } else return null;
    }

    private String getNameForMAC(String name, Set<BluetoothDevice> pairedDevices) {
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {

                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                if (deviceName.equals(name)) {
                    return deviceHardwareAddress;
                }
            }
        }
        return null;
    }

    public boolean isConnected() {
        if (clientSocket != null) {
            return clientSocket.isConnected();
        } else {
            Log.d("clientSocket", "Null");
            return false;
        }
    }

    public void closeSocket(){
        try {
            clientSocket.close();
          //  mHandler.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BluetoothSocket getBluetoothSocket(){
        return clientSocket;
    }
}
