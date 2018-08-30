package com.example.nik.greenery.SetupRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nik.greenery.R;

/**
 * Created by Nik on 29.05.2018.
 */

public class SetHumGroundViewHolder extends RecyclerView.ViewHolder {

    private SeekBar seekBarGround;
    private TextView mValueGnd;

    public SetHumGroundViewHolder(View itemView) {
        super(itemView);
        seekBarGround = itemView.findViewById(R.id.seekBarGroundHum);
        mValueGnd = itemView.findViewById(R.id.value_gnd_set);
    }


    public SeekBar getSeekBarGround() {
        return seekBarGround;
    }

    public void setSeekBarGround(SeekBar seekBarGround) {
        this.seekBarGround = seekBarGround;
    }

    public TextView getmValueGnd() {
        return mValueGnd;
    }

    public void setmValueGnd(TextView mValueGnd) {
        this.mValueGnd = mValueGnd;
    }
}
