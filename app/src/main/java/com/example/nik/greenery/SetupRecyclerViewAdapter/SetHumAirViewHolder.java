package com.example.nik.greenery.SetupRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nik.greenery.R;

/**
 * Created by Nik on 29.05.2018.
 */

public class SetHumAirViewHolder extends RecyclerView.ViewHolder {

    private SeekBar seekBarHumAir;
    private TextView valueHumAir;

    public SetHumAirViewHolder(View itemView) {
        super(itemView);
        seekBarHumAir = itemView.findViewById(R.id.seekBarAirHum);
        valueHumAir = itemView.findViewById(R.id.value_hum_set);
    }

    public SeekBar getSeekBarHumAir() {
        return seekBarHumAir;
    }

    public void setSeekBarHumAir(SeekBar seekBarHumAir) {
        this.seekBarHumAir = seekBarHumAir;
    }

    public TextView getValueHumAir() {
        return valueHumAir;
    }

    public void setValueHumAir(TextView valueHumAir) {
        this.valueHumAir = valueHumAir;
    }
}
