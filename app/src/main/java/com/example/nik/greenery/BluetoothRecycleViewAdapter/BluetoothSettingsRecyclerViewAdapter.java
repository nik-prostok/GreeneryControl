package com.example.nik.greenery.BluetoothRecycleViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nik.greenery.BluetoothConnection.BluetoothConnection;
import com.example.nik.greenery.BluetoothRecycleViewAdapter.NameDeviceList.NameDeviceListRecyclerViewAdapter;
import com.example.nik.greenery.Data.BluetoothDeviceInfo;
import com.example.nik.greenery.R;

import java.util.ArrayList;

/**
 * Created by Nik on 30.04.2018.
 */

public class BluetoothSettingsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<BluetoothDeviceInfo> item;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BluetoothSettingsRecyclerViewAdapter(ArrayList<BluetoothDeviceInfo> item) {
        this.item = item;
    }

    private NameDeviceListRecyclerViewAdapter adapter;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.array_bluetooth_device, parent, false);
                viewHolder = new NameDeviceListViewHolder(v1);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                NameDeviceListViewHolder vh1 = (NameDeviceListViewHolder) holder;
                configureViewHolderBluetoothArray(vh1, position);
                break;
            default:
                break;
        }
    }

    private void configureViewHolderBluetoothArray(NameDeviceListViewHolder vh1, int position) {
        if (item != null) {
            adapter = new NameDeviceListRecyclerViewAdapter(item);
            RecyclerView mRecycleViewList = vh1.getmRecyclerView();
            mRecycleViewList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(mRecycleViewList.getContext());
            mRecycleViewList.setLayoutManager(llm);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecycleViewList.getContext(),
                    llm.getOrientation());
            mRecycleViewList.addItemDecoration(dividerItemDecoration);
            mRecycleViewList.setAdapter(adapter);
        }
    }


    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 0;
            default:
                return -1;
        }
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public void dataChanged(ArrayList<BluetoothDeviceInfo> item) {
        this.item = item;
        notifyDataSetChanged();
    }

    public String getName(){
        return adapter.getName();
    }

    public BluetoothConnection getBluetoothConnection() {
        return adapter.getBluetoothConnection();
    }
}
