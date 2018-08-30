package com.example.nik.greenery.Data;

/**
 * Created by Nik on 12.05.2018.
 */

public class BluetoothDeviceInfo {
    private String nameDevice;
    private String mac;

    public BluetoothDeviceInfo(String nameDevice, String mac) {
        this.nameDevice = nameDevice;
        this.mac = mac;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
