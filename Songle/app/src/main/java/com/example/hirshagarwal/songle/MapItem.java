package com.example.hirshagarwal.songle;

import android.location.Location;

/**
 * Created by Hirsh Agarwal on 9/27/2017.
 */

public class MapItem {
    public final String name;
    public final String type;
    public final Location location;

    private MapItem(String name, String type, Location location){
        this.name = name;
        this.type = type;
        this.location = location;
    }
}
