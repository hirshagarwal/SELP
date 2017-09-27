package com.example.hirshagarwal.songle;

import android.util.Log;
import android.util.Xml;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Hirsh Agarwal on 9/25/2017.
 */

public class CurrentMap{

    private static boolean mapLoaded = false;
    private static String mapString;
    private static URL mapUrl;
    private static DownloadMap downloadMap = new DownloadMap();
    private static ArrayList<MapItem> mapItems = new ArrayList<MapItem>();


    public static void loadMap(){
        // Create the URL
        try{
             mapUrl = new URL("http://www.inf.ed.ac.uk/teaching/courses/selp/data/songs/01/map5.kml");
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        // Load the map
        downloadMap.execute(mapUrl);
    }

    public static void setMap(String mapString){
        CurrentMap.mapString = mapString;

        Log.d("Map Download", "Map set");
        mapLoaded = true;
    }

    // Getters and Setters
    public boolean isMapLoaded(){
        return mapLoaded;
    }


}
