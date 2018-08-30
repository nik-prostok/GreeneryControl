package com.example.nik.greenery.SensorsRecyclerViewAdapter;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nik.greenery.R;
import com.example.nik.greenery.Data.RecycleData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nik on 30.04.2018.
 */

public class SensorsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private String nameDevice;

    // The items to display in your RecyclerView
    private RecycleData item;

    boolean manualMode = false;

    private final int NAME_DEVICE = 0, HUM_AND_TEMP = 1, LIGHT = 2, HUM_GROUND = 3;

    Handler mHandler;

    boolean stateHeatSwitch;
    boolean stateHumSwitch;
    boolean stateGroundSwitch;
    boolean stateLightingSwitch;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SensorsRecyclerViewAdapter(RecycleData item, Handler handlerSet) {
        this.item = item;
        this.mHandler = handlerSet;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
           /* case NAME_DEVICE:
                View v1 = inflater.inflate(R.layout.name_device, parent, false);
                viewHolder = new NameDeviceViewHolder(v1);
                break;*/
            case HUM_AND_TEMP:
                View v2 = inflater.inflate(R.layout.temperature_and_hum, parent,false);
                viewHolder = new TempHumViewHolder(v2);
                break;
            case LIGHT:
                View v3 = inflater.inflate(R.layout.lux, parent, false);
                viewHolder = new LightViewHolder(v3);
                break;
            case HUM_GROUND:
                View v4 = inflater.inflate(R.layout.hum_ground, parent, false);
                viewHolder = new HumGroundViewHolder(v4);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
          /*  case NAME_DEVICE:
                NameDeviceViewHolder vh1 = (NameDeviceViewHolder) holder;
                configureViewHolderNameDevice(vh1, position);
                break;*/
            case HUM_AND_TEMP:
                TempHumViewHolder vh2 = (TempHumViewHolder) holder;
                configureViewHolderTempHum(vh2, position);
                break;
            case LIGHT:
                LightViewHolder vh3 = (LightViewHolder)holder;
                configureViewHolderLight(vh3, position);
                break;
            case HUM_GROUND:
                HumGroundViewHolder vh4 = (HumGroundViewHolder)holder;
                configureViewHolderGround(vh4, position);
                break;
            default:
                break;
        }
    }

    private void configureViewHolderGround(HumGroundViewHolder vh4, int position) {
        if (item != null){
            //GROUND HUM
            if (!item.getHumGround().equals("N/A")){
                vh4.getHumGroundProgressBar().setIndeterminate(false);
                vh4.getHumGroundTextValue().setText(item.getHumGround());
               vh4.getHumGroundProgressBar().setProgress(Float.parseFloat(item.getHumGround()));
            } else {
                vh4.getHumGroundProgressBar().setIndeterminate(true);
                vh4.getHumGroundTextValue().setText(item.getHumGround());
        //        vh4.getHumGroundProgressBar().setProgress(Float.parseFloat(recycleData.getHumGround()));
            }

            if (!manualMode){
                vh4.getHumGroundSwitch().setClickable(false);
                vh4.getHumGroundSwitch().setChecked(item.getWatering());
                stateGroundSwitch = item.getWatering();
            } else {
                vh4.getHumGroundSwitch().setClickable(true);
                vh4.getHumGroundSwitch().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stateGroundSwitch = !stateGroundSwitch;
                        Message writtenMsg = mHandler.obtainMessage(1, getJSONStringManualMode());
                        mHandler.sendMessage(writtenMsg);
                        /*if (manualMode) {
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("jsonManualMode", getJSONStringManualMode());
                                    Message writtenMsg = mHandler.obtainMessage(1, getJSONStringManualMode());
                                    mHandler.sendMessage(writtenMsg);
                                }
                            };
                            thread.start();
                        }*/
                    }
                });
            }
        }
    }

    private void configureViewHolderLight(LightViewHolder vh3, int position) {
        if (item != null){
            //LIGHT
            if (!item.getLight().equals("N/A")){
                vh3.getLightProgressBar().setIndeterminate(false);
                vh3.getLightTextValue().setText(item.getLight());
                vh3.getLightProgressBar().setProgress(Float.parseFloat(item.getLight()));
            } else {
                vh3.getLightProgressBar().setIndeterminate(true);
                vh3.getLightTextValue().setText(item.getLight());
             //   vh3.getLightProgressBar().setProgress(Float.parseFloat(recycleData.getLight()));
            }

            if (!manualMode){
                vh3.getSwitchLight().setClickable(false);
                vh3.getSwitchLight().setChecked(item.getLighting());
                stateLightingSwitch = item.getLighting();
            } else {
                vh3.getSwitchLight().setClickable(true);
                vh3.getSwitchLight().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stateLightingSwitch = !stateLightingSwitch;
                        Message writtenMsg = mHandler.obtainMessage(1, getJSONStringManualMode());
                        mHandler.sendMessage(writtenMsg);
                        /*if (manualMode) {
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("jsonManualMode", getJSONStringManualMode());
                                    Message writtenMsg = mHandler.obtainMessage(1, getJSONStringManualMode());
                                    mHandler.sendMessage(writtenMsg);
                                }
                            };
                            thread.start();
                        }*/
                    }
                });
            }
        }
    }

    private void configureViewHolderTempHum(TempHumViewHolder vh2, int position) {
        //RecycleData recycleData = (RecycleData)items/*.get(position)*/;
        if (item != null){
            Log.d("manualMode", String.valueOf(manualMode));
            //HUM
            if (!item.getHumAir().equals("N/A")){
                vh2.getHumProgressBar().setIndeterminate(false);
                vh2.getHum().setText(item.getHumAir());
                vh2.getHumProgressBar().setProgress(Float.parseFloat(item.getHumAir()));
            } else {
                vh2.getHumProgressBar().setIndeterminate(true);
                vh2.getHum().setText(item.getHumAir());
           //     vh2.getHumProgressBar().setProgress(Float.parseFloat(recycleData.getHumAir()));
            }
            if (!manualMode){
                vh2.getSwitchAero().setClickable(false);
                vh2.getSwitchAero().setChecked(item.getAeration());
                stateHumSwitch = item.getAeration();
            } else {
                vh2.getSwitchAero().setClickable(true);
                vh2.getSwitchAero().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stateHumSwitch = !stateHumSwitch;
                        Message writtenMsg = mHandler.obtainMessage(1, getJSONStringManualMode());
                        mHandler.sendMessage(writtenMsg);
                    }
                });
            }

            //TEMP
            if (!item.getTemp().equals("N/A")) {
                vh2.getTempProgressBar().setIndeterminate(false);
                vh2.getTemp().setText(item.getTemp());
                vh2.getTempProgressBar().setProgress(Float.parseFloat(item.getTemp()));
            } else {
                vh2.getTempProgressBar().setIndeterminate(true);
                vh2.getTemp().setText(item.getTemp());
          //      vh2.getTempProgressBar().setProgress(Float.parseFloat(recycleData.getTemp()));
            }

            if (!manualMode){
                vh2.getSwitchHeat().setClickable(false);
                vh2.getSwitchHeat().setChecked(item.getHeat());
                stateHeatSwitch = item.getHeat();
            } else {
                vh2.getSwitchHeat().setClickable(true);
                vh2.getSwitchHeat().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stateHeatSwitch = !stateHeatSwitch;
                        Message writtenMsg = mHandler.obtainMessage(1, getJSONStringManualMode());
                        mHandler.sendMessage(writtenMsg);
                       /* if (manualMode) {
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("jsonManualMode", getJSONStringManualMode());
                                  Message writtenMsg = mHandler.obtainMessage(1, getJSONStringManualMode());
                                    mHandler.sendMessage(writtenMsg);
                                }
                            };
                            thread.start();
                        }*/
                    }
                });
            }
        }
    }


    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        Log.d("Position", String.valueOf(position));

        switch (position){
         /*   case 0:
                return  NAME_DEVICE;*/
            case 0:
                return HUM_AND_TEMP;
            case 1:
                return LIGHT;
            case 2:
                return  HUM_GROUND;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void dataChanged( RecycleData item){

        this.item = item;
        notifyDataSetChanged();
    }

    public void setManualMode(boolean manualMode) {
        this.manualMode = manualMode;
    }

    private String getJSONStringManualMode(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mode", "manual");
            if (stateHeatSwitch){
                jsonObject.put("h", "1");
            } else {
                jsonObject.put("h", "0");
            }

            if (stateGroundSwitch){
                jsonObject.put("w", "1");
            } else {
                jsonObject.put("w", "0");
            }

            if (stateHumSwitch){
                jsonObject.put("a", "1");
            } else {
                jsonObject.put("a", "0");
            }

            if (stateLightingSwitch){
                jsonObject.put("l", "1");
            } else {
                jsonObject.put("l", "0");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonStringManual", jsonObject.toString());
        String send = new String(jsonObject.toString() + '\n');
        return send;
    }
}
