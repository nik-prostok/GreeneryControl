package com.example.nik.greenery.SetupRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nik.greenery.R;

import org.w3c.dom.Text;

/**
 * Created by Nik on 29.05.2018.
 */

public class SetAirTempViewHolder extends RecyclerView.ViewHolder {

    private SeekBar seekBarTemp;
    private TextView mTempValue;


    public SetAirTempViewHolder(View itemView) {
        super(itemView);
        seekBarTemp = itemView.findViewById(R.id.seekBarTemp);
        mTempValue = itemView.findViewById(R.id.value_temp_set);
    }

    public SeekBar getSeekBarTemp() {
        return seekBarTemp;
    }

    public void setSeekBarTemp(SeekBar seekBarTemp) {
        this.seekBarTemp = seekBarTemp;
    }

    public TextView getmTempValue() {
        return mTempValue;
    }

    public void setmTempValue(TextView mTempValue) {
        this.mTempValue = mTempValue;
    }
}
