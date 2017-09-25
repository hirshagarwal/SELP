package com.example.hirshagarwal.songle;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by Hirsh Agarwal on 9/25/2017.
 */

public class CurrentMap extends AsyncTask<String, Void, String>{

    private boolean mapLoaded = false;

    public void loadMap(){
        // Load the map

    }

    // Interface Methods
    protected String doInBackground(String... urls){
        return "";
    }

    protected void onPostExecute(){
        
    }

    // Getters and Setters
    public boolean isMapLoaded(){
        return mapLoaded;
    }


}
