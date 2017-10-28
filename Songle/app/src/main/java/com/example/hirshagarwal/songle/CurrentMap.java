package com.example.hirshagarwal.songle;

import android.location.Location;
import android.util.Log;
import android.util.Xml;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Hirsh Agarwal on 9/25/2017.
 */

public class CurrentMap{

    private static boolean mapLoaded = false;
    private static String mapString;
    private static URL mapUrl;
    private static URL wordsUrl;
    private static DownloadMap downloadMap = new DownloadMap();
    private static ArrayList<MapItem> mapItems = new ArrayList<MapItem>();
    private static ArrayList<MapIconStyle> mapIconStyles = new ArrayList<MapIconStyle>();
    private static MapView map;

    public static void loadMap() {
        // Create the URL

        try {
            mapUrl = new URL("http://www.inf.ed.ac.uk/teaching/courses/selp/data/songs/01/map5.kml");
            wordsUrl = new URL("http://www.inf.ed.ac.uk/teaching/courses/selp/data/songs/01/words.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // Load the map
        downloadMap.execute(mapUrl);
        // Load the words

    }

    public static void setMap(String mapString){
        // Set the map string
        CurrentMap.mapString = mapString;
        Log.d("Map Download", "Map set");
        mapLoaded = true;
    }


    // Getters and Setters
    public static boolean isMapLoaded(){
        return mapLoaded;
    }

    public static ArrayList<MapItem> getMapItems(){
        return mapItems;
    }

    public static String getMapString(){
        return mapString;
    }


}
