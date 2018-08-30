package com.example.nik.greenery.BluetoothRecycleViewAdapter.NameDeviceList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.nik.greenery.R;

/**
 * Created by Nik on 16.05.2018.
 */

public class NameDeviceTextHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    public NameDeviceTextHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.name_bluetooth_device);
    }

    public TextView getmTextView() {
        return mTextView;
    }

    public void setmTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }
}
