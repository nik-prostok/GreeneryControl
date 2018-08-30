package com.example.nik.greenery.BluetoothRecycleViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.nik.greenery.R;

/**
 * Created by Nik on 12.05.2018.
 */

public class NameDeviceListViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView mRecyclerView;

    public NameDeviceListViewHolder(View itemView) {
        super(itemView);
        mRecyclerView = itemView.findViewById(R.id.list_name_rv);
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }
}
