package com.example.hirshagarwal.songle;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Hirsh Agarwal on 9/27/2017.
 */

public class MapItem {
    public String name;
    public String type;
    public LatLng location;

    // Getters and Setters
    public void setName(String name){
        this.name = name;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setLocation(LatLng location){
        this.location = location;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public LatLng getLocation(){
        return location;
    }
}
