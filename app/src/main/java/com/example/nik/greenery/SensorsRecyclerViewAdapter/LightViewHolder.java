package com.example.nik.greenery.SensorsRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.nik.greenery.R;

/**
 * Created by Nik on 04.05.2018.
 */

public class LightViewHolder extends RecyclerView.ViewHolder {
    private CircularProgressBar lightProgressBar;
    private TextView lightTextValue;
    private Switch switchLight;

    public LightViewHolder(View itemView) {
        super(itemView);
        lightProgressBar = (CircularProgressBar)itemView.findViewById(R.id.progress_bar_lighting);
        lightTextValue = (TextView)itemView.findViewById(R.id.light_value);
        switchLight = (Switch)itemView.findViewById(R.id.switchLight);
    }

    public CircularProgressBar getLightProgressBar() {
        return lightProgressBar;
    }

    public void setLightProgressBar(CircularProgressBar lightProgressBar) {
        this.lightProgressBar = lightProgressBar;
    }

    public TextView getLightTextValue() {
        return lightTextValue;
    }

    public void setLightTextValue(TextView lightTextValue) {
        this.lightTextValue = lightTextValue;
    }

    public Switch getSwitchLight() {
        return switchLight;
    }

    public void setSwitchLight(Switch switchLight) {
        this.switchLight = switchLight;
    }
}
