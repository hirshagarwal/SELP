package com.example.hirshagarwal.songle;

import android.location.Location;
import android.util.Log;
import android.util.Xml;

import com.google.android.gms.maps.model.LatLng;

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
    private static DownloadMap downloadMap = new DownloadMap();
    private static ArrayList<MapItem> mapItems = new ArrayList<MapItem>();
    private static ArrayList<MapIconStyle> mapIconStyles = new ArrayList<MapIconStyle>();

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
        // Parse Map String
        Log.d("XML", "Parsing XML Data");
        try {
            parseXML();
        } catch (IOException e){
            e.printStackTrace();
        } catch (org.xmlpull.v1.XmlPullParserException e){
            e.printStackTrace();
        }
        Log.d("Map Download", "Map set");
        mapLoaded = true;
    }

    public static void parseXML() throws XmlPullParserException, IOException{
        // Parse the xml
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false); // Not using namespaces
        XmlPullParser xpp = factory.newPullParser();

        InputStream stream = new ByteArrayInputStream(mapString.getBytes(StandardCharsets.UTF_8.name()));
        xpp.setInput(new StringReader(mapString));
        MapItem mapItem = null;
        MapIconStyle mapIconStyle = null;
        int eventType = xpp.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            String tagName = xpp.getName();
            if (eventType == XmlPullParser.START_DOCUMENT){
                Log.d("XML", "Start Document");
            }
            if(eventType == XmlPullParser.START_TAG){
                // Case for Icon Style
                if (tagName.equalsIgnoreCase("Style")){
                    mapIconStyle = new MapIconStyle();
                    mapIconStyle.setName(xpp.getAttributeValue(null, "id"));
                } else if (tagName.equalsIgnoreCase("IconStyle")){
                    // Don't really do anything, just keep parsing
                } else if(tagName.equalsIgnoreCase("Scale")){
                    eventType = xpp.next();
                    if (eventType == XmlPullParser.TEXT) {
                        Log.d("XML", xpp.getText());
                        mapIconStyle.setScale(Double.parseDouble(xpp.getText()));
                    }
                } else if (tagName.equalsIgnoreCase("Icon")){
                    // Do nothing - keep parsing
                } else if (tagName.equalsIgnoreCase("href")){
                    // Set the link
                    mapIconStyle.setLink(xpp.getText());
                }
                // Case for Placemark
                else if (tagName.equalsIgnoreCase("Placemark")){
                    mapItem = new MapItem();
                } else if (tagName.equalsIgnoreCase("name")){
                    eventType = xpp.next();
                    if(eventType == XmlPullParser.TEXT){
                        mapItem.setName(xpp.getText());
                    }
                } else if (tagName.equalsIgnoreCase("description")){
                    eventType = xpp.next();
                    if(eventType == XmlPullParser.TEXT){
                        mapItem.setType(xpp.getText());
                    }
                } else if (tagName.equalsIgnoreCase("Point")){
                    // Do nothing
                } else if (tagName.equalsIgnoreCase("coordinates")){
                    eventType = xpp.next();
                    if(eventType == XmlPullParser.TEXT){
                        String coordinatePair = xpp.getText();
                        double latitude = 0;
                        double longitude = 0;
                        String[] latLong = coordinatePair.split(",");
                        latitude = Double.parseDouble(latLong[0]);
                        longitude = Double.parseDouble(latLong[1]);
                        LatLng coordinates = new LatLng(longitude, latitude);
                        mapItem.setLocation(coordinates);
                        Log.d("XML", "Coordinates Added: " + coordinates.toString());
                    }

                }
            } else if (eventType == XmlPullParser.END_TAG){
                if (tagName.equalsIgnoreCase("Style")){
                    mapIconStyles.add(mapIconStyle);
                    mapIconStyle = null;
                } else if (tagName.equalsIgnoreCase("Placemark")){
                    mapItems.add(mapItem);
                    mapItem = null;
                }
            }
            eventType = xpp.next();
            Log.d("XML", "Next Line");
        }
        Log.d("XML Parser", "Parse Completed");
    }

    // Getters and Setters
    public static boolean isMapLoaded(){
        return mapLoaded;
    }

    public static ArrayList<MapItem> getMapItems(){
        return mapItems;
    }


}
