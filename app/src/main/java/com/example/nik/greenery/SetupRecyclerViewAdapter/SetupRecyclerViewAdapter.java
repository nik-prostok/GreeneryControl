package com.example.nik.greenery.SetupRecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.nik.greenery.Activity.MainActivity;
import com.example.nik.greenery.BluetoothConnection.BluetoothService;
import com.example.nik.greenery.Data.RecycleData;
import com.example.nik.greenery.R;
import com.example.nik.greenery.SensorsRecyclerViewAdapter.HumGroundViewHolder;
import com.example.nik.greenery.SensorsRecyclerViewAdapter.LightViewHolder;
import com.example.nik.greenery.SensorsRecyclerViewAdapter.TempHumViewHolder;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nik on 29.05.2018.
 */

public class SetupRecyclerViewAdapter extends RecyclerView.Adapter {

    private final int TEMP = 0, HUM_AIR = 1, HUM_GROUND = 2, LIGHT = 3, APPLY = 4;

    private int tempValue = 15;
    private int humGroundValue = 0;
    private int hourNight = 22;
    private int hourDay = 7;
    private int minuteNight = 0;
    private int minuteDay = 0;

    private Handler mHandler;

    boolean syncTime = true;

    Calendar dateAndTime=Calendar.getInstance();

    public SetupRecyclerViewAdapter(Handler mHandler, boolean syncTime){
        this.mHandler = mHandler;
        this.syncTime = syncTime;
    }

    private String getJSONStringSetTimeMode(){

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("mode", "setTime");
            jsonObject.put("hour", hour);
            jsonObject.put("minute", minute);
            jsonObject.put("second", second);
        } catch (JSONException e){
            e.printStackTrace();
        }

        Log.d("jsonStringSetTime", jsonObject.toString());
        return new String(jsonObject.toString() + "\n");
    }

    private String getJSONStringSetupMode(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mode", "st");
            jsonObject.put("t", tempValue);
            jsonObject.put("g", humGroundValue);
            jsonObject.put("hn", hourNight);
            jsonObject.put("hd", hourDay);
            jsonObject.put("md", minuteDay);
            jsonObject.put("mn", minuteNight);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonStringSetup", jsonObject.toString());
        Log.d("sizeStringSetup", String.valueOf(jsonObject.toString().length()));
        String send = new String(jsonObject.toString() + "\n");
        return send;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case TEMP:
                View v1 = inflater.inflate(R.layout.set_temp, parent, false);
                viewHolder = new SetAirTempViewHolder(v1);
                break;
            /*case HUM_AIR:
                View v2 = inflater.inflate(R.layout.set_hum_air, parent,false);
                viewHolder = new SetHumAirViewHolder(v2);
                break;*/
            case HUM_GROUND:
                View v3 = inflater.inflate(R.layout.set_hum_ground, parent, false);
                viewHolder = new SetHumGroundViewHolder(v3);
                break;
            case LIGHT:
                View v4 = inflater.inflate(R.layout.set_light, parent, false);
                viewHolder = new SetLightViewHolder(v4);
                break;
            case APPLY:
                View v5 = inflater.inflate(R.layout.apply_settings, parent,false);
                viewHolder = new ApplySettingsViewHolder(v5);
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TEMP:
                SetAirTempViewHolder vh1 = (SetAirTempViewHolder) holder;
                configureViewHolderSetTemp(vh1, position);
                break;
            /*case HUM_AIR:
                SetHumAirViewHolder vh2 = (SetHumAirViewHolder) holder;
                configureViewHolderHumAir(vh2, position);
                break;*/
            case HUM_GROUND:
                SetHumGroundViewHolder vh3 = (SetHumGroundViewHolder)holder;
                configureViewHolderSetGround(vh3, position);
                break;
            case LIGHT:
                SetLightViewHolder vh4 = (SetLightViewHolder)holder;
                configureViewHolderSetLight(vh4, position);
                break;
            case APPLY:
                ApplySettingsViewHolder vh5 = (ApplySettingsViewHolder)holder;
                configureViewHolderApplySettings(vh5, position);
            default:
                break;
        }
    }

    private void configureViewHolderApplySettings(ApplySettingsViewHolder vh5, int position) {
        vh5.getmApplySettings().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message writtenMsg = mHandler.obtainMessage(1, getJSONStringSetupMode());
                mHandler.sendMessage(writtenMsg);


                if (syncTime) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d("jsonSetTime", getJSONStringSetTimeMode());
                            Message writtenMsg = mHandler.obtainMessage(1, getJSONStringSetTimeMode());
                            mHandler.sendMessage(writtenMsg);
                        }
                    };
                    thread.start();
                }
            }
        });
    }

    private void configureViewHolderSetGround(SetHumGroundViewHolder vh4, int position) {
        final TextView mValueGnd = vh4.getmValueGnd();
        SeekBar seekBarGnd = vh4.getSeekBarGround();
        seekBarGnd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                humGroundValue = progress;
                mValueGnd.setText(new String(String.valueOf(humGroundValue)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void configureViewHolderSetLight(SetLightViewHolder vh3, int position) {
        final Button buttonDay = vh3.getButtonDay();
        final Button buttonNight = vh3.getButtonNight();

        final TimePickerDialog.OnTimeSetListener tDay=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                //dateAndTime.set(Calendar.MINUTE, minute);
                hourDay = hourOfDay;
                minuteDay = minute;
                Log.d("hourOfDay", String.valueOf(hourOfDay));
                Log.d("minuteDay", String.valueOf(minute));
            }
        };

        final TimePickerDialog.OnTimeSetListener tNight=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                //dateAndTime.set(Calendar.MINUTE, minute);
                hourNight = hourOfDay;
                minuteNight = minute;
                Log.d("hourOfNight", String.valueOf(hourOfDay));
                Log.d("minuteNight", String.valueOf(minute));
            }
        };

        buttonDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), tDay,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show();
            }
        });

        buttonNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), tNight,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show();
            }
        });

    }

    private void configureViewHolderHumAir(SetHumAirViewHolder vh2, final int position) {
        final TextView mValueHumAir = vh2.getValueHumAir();
        SeekBar seekBarHumAir = vh2.getSeekBarHumAir();
        seekBarHumAir.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               // humAirValue = String.valueOf(progress * 5);
               // mValueHumAir.setText(new String(humAirValue + " %"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void configureViewHolderSetTemp(SetAirTempViewHolder vh1, int position) {
        final TextView mValueTemp = vh1.getmTempValue();
        SeekBar seekBarTemp = vh1.getSeekBarTemp();
        seekBarTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tempValue = Integer.valueOf(progress  + 15);
                mValueTemp.setText(new String(tempValue + " °C"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public int getItemViewType(int position) {
        Log.d("Position", String.valueOf(position));

        switch (position){
            case 0:
                return TEMP;
           /* case 1:
                return HUM_AIR;*/
            case 1:
                return HUM_GROUND;
            case 2:
                return  LIGHT;
            case 3:
                return APPLY;
            default:
                return -1;
        }
    }
}
