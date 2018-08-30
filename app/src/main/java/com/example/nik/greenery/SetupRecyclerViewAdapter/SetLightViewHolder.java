package com.example.nik.greenery.SetupRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nik.greenery.R;

/**
 * Created by Nik on 29.05.2018.
 */

public class SetLightViewHolder extends RecyclerView.ViewHolder {

    private Button buttonDay;
    private Button buttonNight;

    public SetLightViewHolder(View itemView) {
        super(itemView);
        buttonDay = itemView.findViewById(R.id.buttonSetDay);
        buttonNight = itemView.findViewById(R.id.buttonSetNight);
    }

    public Button getButtonDay() {
        return buttonDay;
    }

    public void setButtonDay(Button buttonDay) {
        this.buttonDay = buttonDay;
    }

    public Button getButtonNight() {
        return buttonNight;
    }

    public void setButtonNight(Button buttonNight) {
        this.buttonNight = buttonNight;
    }
}
