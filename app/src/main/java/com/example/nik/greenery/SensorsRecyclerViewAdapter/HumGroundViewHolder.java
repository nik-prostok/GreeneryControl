package com.example.nik.greenery.SensorsRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.nik.greenery.R;

/**
 * Created by Nik on 04.05.2018.
 */

public class HumGroundViewHolder extends RecyclerView.ViewHolder {

    private CircularProgressBar humGroundProgressBar;
    private TextView humGroundTextValue;
    private Switch humGroundSwitch;

    public HumGroundViewHolder(View itemView) {
        super(itemView);
        humGroundProgressBar = (CircularProgressBar)itemView.findViewById(R.id.progress_bar_hum_ground);
        humGroundTextValue = (TextView)itemView.findViewById(R.id.hum_value);
        humGroundSwitch = itemView.findViewById(R.id.switchGround);
    }

    public CircularProgressBar getHumGroundProgressBar() {
        return humGroundProgressBar;
    }

    public void setHumGroundProgressBar(CircularProgressBar humGroundProgressBar) {
        this.humGroundProgressBar = humGroundProgressBar;
    }

    public TextView getHumGroundTextValue() {
        return humGroundTextValue;
    }

    public void setHumGroundTextValue(TextView humGroundTextValue) {
        this.humGroundTextValue = humGroundTextValue;
    }

    public Switch getHumGroundSwitch() {
        return humGroundSwitch;
    }

    public void setHumGroundSwitch(Switch humGroundSwitch) {
        this.humGroundSwitch = humGroundSwitch;
    }
}
