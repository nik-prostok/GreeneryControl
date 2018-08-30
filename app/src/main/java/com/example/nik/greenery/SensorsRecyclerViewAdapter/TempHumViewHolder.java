package com.example.nik.greenery.SensorsRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.nik.greenery.R;

/**
 * Created by Nik on 30.04.2018.
 */

public class TempHumViewHolder extends RecyclerView.ViewHolder {

    private CircularProgressBar tempProgressBar;
    private CircularProgressBar humProgressBar;
    private TextView nameAir;
    private TextView temp;
    private TextView hum;
    private TextView tempText;
    private TextView humText;

    private Switch switchHeat;
    private Switch switchAero;

    public TempHumViewHolder(View itemView) {
        super(itemView);
        tempProgressBar = itemView.findViewById(R.id.progress_bar_temperature);
        humProgressBar = itemView.findViewById(R.id.progress_bar_humidyty);
        nameAir = itemView.findViewById(R.id.air);
        temp = itemView.findViewById(R.id.temp_text);
        hum = itemView.findViewById(R.id.hum_text);
        tempText = itemView.findViewById(R.id.temperature);
        humText = itemView.findViewById(R.id.humidity);
        switchHeat = itemView.findViewById(R.id.switchTemp);
        switchAero = itemView.findViewById(R.id.switchHum);
    }

    public CircularProgressBar getTempProgressBar(){
        return this.tempProgressBar;
    }

    public CircularProgressBar getHumProgressBar(){
        return this.humProgressBar;
    }

    public TextView getNameAir(){
        return this.nameAir;
    }

    public TextView getTemp(){
        return this.temp;
    }

    public TextView getHum(){
        return this.hum;
    }

    public TextView getTempText(){
        return this.tempText;
    }

    public TextView getHumText(){
        return this.humText;
    }

    public void setHumProgressBar(CircularProgressBar humProgressBar) {
        this.humProgressBar = humProgressBar;
    }

    public void setNameAir(TextView nameAir) {
        this.nameAir = nameAir;
    }

    public void setTempProgressBar(CircularProgressBar tempProgressBar) {
        this.tempProgressBar = tempProgressBar;
    }

    public void setHum(TextView hum) {
        this.hum = hum;
    }

    public void setTemp(TextView temp) {
        this.temp = temp;
    }

    public void setHumText(TextView humText) {
        this.humText = humText;
    }

    public void setTempText(TextView tempText) {
        this.tempText = tempText;
    }

    public Switch getSwitchHeat() {
        return switchHeat;
    }

    public void setSwitchHeat(Switch switchHeat) {
        this.switchHeat = switchHeat;
    }

    public Switch getSwitchAero() {
        return switchAero;
    }

    public void setSwitchAero(Switch switchAero) {
        this.switchAero = switchAero;
    }
}
