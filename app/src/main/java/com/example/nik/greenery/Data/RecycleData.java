package com.example.nik.greenery.Data;

/**
 * Created by Nik on 29.04.2018.
 */

public class RecycleData {
    private String temp;
    private String humAir;
    private String humGround;
    private String light;

    private boolean heat;
    private boolean aeration;
    private boolean lighting;
    private boolean watering;

    public RecycleData(String temp, String humAir, String humGround, String light, boolean heat, boolean aeration, boolean lighting, boolean watering){
        this.temp = temp;
        this.humAir = humAir;
        this.humGround = humGround;
        this.light = light;
        this.heat = heat;
        this.aeration = aeration;
        this.lighting = lighting;
        this.watering = watering;
    }

    public RecycleData(){

    }

    public String getTemp(){
        return this.temp;
    }

    public String getHumAir(){
        return this.humAir;
    }

    public String getHumGround(){
        return this.humGround;
    }

    public String getLight(){
        return this.light;
    }


    public boolean getHeat() {
        return heat;
    }

    public boolean getAeration() {
        return aeration;
    }

    public boolean getLighting() {
        return lighting;
    }

    public boolean getWatering() {
        return watering;
    }
}
