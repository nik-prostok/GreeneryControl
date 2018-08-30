package com.example.nik.greenery.Fragments;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nik.greenery.BluetoothConnection.BluetoothConnection;
import com.example.nik.greenery.BluetoothRecycleViewAdapter.BluetoothSettingsRecyclerViewAdapter;
import com.example.nik.greenery.Data.BluetoothDeviceInfo;
import com.example.nik.greenery.R;

import java.util.ArrayList;
import java.util.Set;

import static java.lang.String.valueOf;

/**
 * Created by Nik on 11.05.2018.
 */

public class BluetoothFragment extends Fragment {

    RecyclerView rv;
    BluetoothSettingsRecyclerViewAdapter adapter;

    ArrayList<BluetoothDeviceInfo> mDeviceInfoList = new ArrayList<>();
    Set<BluetoothDevice> pairedDevices;

    private OnFragmentInteractionListener mListener;


    public static BluetoothFragment newInstance() {
        BluetoothFragment catFragment = new BluetoothFragment();
        Bundle args = new Bundle();
        //args.putString("Name", name);
        catFragment.setArguments(args);
        return catFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        int SomeInt = getArguments().getInt("someInt", 0);
        String someString = getArguments().getString("someString", "");

        Log.d("SomeInt", String.valueOf(SomeInt));
        Log.d("someString", someString);

        BluetoothConnection mBluetoothConnection = new BluetoothConnection();
        pairedDevices = mBluetoothConnection.getNearbyDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.d("deviceName", deviceName);
                mDeviceInfoList.add(new BluetoothDeviceInfo(deviceName, deviceHardwareAddress));
            }
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BluetoothSettingsRecyclerViewAdapter(mDeviceInfoList);
        Log.d("State","onActivityCreated");
        rv = getActivity().findViewById(R.id.rv_bluetooth);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

    }

    @Override
    public void onResume() {

        super.onResume();
        rv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bluetooth_layout, container, false);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mListener = (OnFragmentInteractionListener)context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {

        super.onStart();

    }

    public void updateData(){
        String curData = "27.05.2018";
        mListener.onFragmentInteraction(curData);
    }

    public interface OnFragmentInteractionListener{
        void onFragmentInteraction(String link);
    }

    public String getName(){
        if (adapter == null){
            return null;
        } else return adapter.getName();
    }

    public BluetoothConnection getBluetoothConnection(){
        if (adapter == null){
            return null;
        } else return adapter.getBluetoothConnection();
    }

}
