package com.example.copd_app;

import androidx.appcompat.app.AppCompatActivity;

public class Weather_SiteSample extends AppCompatActivity {
    private String site;
    private float longtitude;
    private float latitude;

    public String getsite(){
        return site;
    }

    public void setSite(String site){
        this.site = site;
    }

    public double getLongtitude(){
        return longtitude;
    }

    public void setLongtitude(float longtitude){
        this.longtitude = longtitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(float latitude){
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "WeatherSitesample("
                + site + ","
                + longtitude + ","
                + latitude +
                ")";
    }
}
