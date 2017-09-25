package com.example.hirshagarwal.songle;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Hirsh Agarwal on 9/25/2017.
 */

public class CurrentMap {

    private boolean mapLoaded = false;
    private String mapUrl = "http://www.inf.ed.ac.uk/teaching/courses/selp/data/songs/01/map5.kml";

    NetworkHandler networkHandler = new NetworkHandler(mapUrl);

    public void loadMap(){
        // Load the map

    }

    // Getters and Setters
    public boolean isMapLoaded(){
        return mapLoaded;
    }


}
