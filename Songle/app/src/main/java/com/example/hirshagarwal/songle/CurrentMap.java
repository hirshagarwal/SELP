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
    private static boolean iconsLoaded = false;
    private static String mapString;
    private static String songsString;
    private static String wordsString;
    private static URL mapUrl;
    private static URL wordsUrl;
    private static DownloadMap downloadMap = new DownloadMap();
    private static DownloadSongs downloadSongs = new DownloadSongs();
    private static DownloadWords downloadWords = new DownloadWords();
    private static ArrayList<MapItem> mapItems = new ArrayList<MapItem>();
    private static ArrayList<MapIconStyle> mapIconStyles = new ArrayList<MapIconStyle>();
    private static ArrayList<Song> songList = new ArrayList<Song>();
    private static MapView map;
    private static Song currentSong;
    private static int mapNumber = 1;

    public static void loadData(){
        downloadSongs.execute();
    }

    public static void loadMap() {
        // Create the URL
        int map = 0;
        // First parse the songs
        parseSongs(songsString);
        int numSongs = songList.size();
        // Random Song Number
        int randomNum = (int)(Math.random() * numSongs);
        currentSong = songList.get(randomNum);

        try {
            mapUrl = new URL("http://www.inf.ed.ac.uk/teaching/courses/selp/data/songs/" + currentSong.getNumber() + "/map" + mapNumber + ".kml");
            wordsUrl = new URL("http://www.inf.ed.ac.uk/teaching/courses/selp/data/songs/" + currentSong.getNumber() + "/words.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // Load the map
        downloadMap.execute(mapUrl);
        // Load the words
        downloadWords.execute(wordsUrl);
    }

    public static void setMap(String mapString){
        // Set the map string
        CurrentMap.mapString = mapString;
//        parseMap(mapString);
        Log.d("Map Download", "Map set");
//        downloadIcons();
        mapLoaded = true;
    }

    private static void downloadIcons(){
        Log.d("Number of Styles", mapIconStyles.size() + "");
        for(int i=0; i<mapIconStyles.size(); i++){
            DownloadBitmap imageDownload = new DownloadBitmap(i);
            try{
                Log.d("URL", mapIconStyles.get(i).getLink());
                URL iconUrl = new URL(mapIconStyles.get(i).getLink());
                imageDownload.execute(iconUrl);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }
        }
        iconsLoaded = true;
    }

    private static void parseSongs(String songsString){
        Log.d("Parse", "Songs");
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(songsString));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    String tagName = xpp.getName();
                    if(tagName.equals("Song")){
                        Song newSong = new Song();
                        // Get the number
                        while(xpp.getName() == null || !xpp.getName().equals("Number")){
                            xpp.next();
                        }
                        xpp.next();
                        newSong.setNumber(xpp.getText());

                        // Get the Song Artist
                        while(xpp.getName() == null || !xpp.getName().equals("Artist")){
                            xpp.next();
                        }
                        xpp.next();
                        newSong.setArtist(xpp.getText());
                        // Get the song title
                        while(xpp.getName() == null || !xpp.getName().equals("Title")){
                            xpp.next();
                        }
                        xpp.next();
                        newSong.setTitle(xpp.getText());

                        songList.add(newSong);
                    }
                }
                eventType = xpp.next();
            }
        } catch (org.xmlpull.v1.XmlPullParserException e){
            e.printStackTrace();
        } catch (java.io.IOException e){
            e.printStackTrace();
        }
    }

    private static void parseMap(String mapString){
        System.out.println("Parsing Map");
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(mapString));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
//                System.out.println("XPP - " + xpp.get)
                if(eventType == XmlPullParser.START_TAG){
//                    System.out.println("XPP: " + xpp.getName());
                    String tagName = xpp.getName();
                    if(tagName.equals("Style")){
                        MapIconStyle newStyle = new MapIconStyle();
                        newStyle.setName(xpp.getAttributeValue(null, "id"));
                        // Get the Scale and Icon
                            // Move forwards to get the scale
                            xpp.next();
                            xpp.next();
                            xpp.next();
                            xpp.next();
                            xpp.next();
                        newStyle.setScale(Double.parseDouble(xpp.getText()));
                        // Get Icon
                            while(xpp.getName() == null || !xpp.getName().equals("href")){
                                xpp.next();
                            }
                            xpp.next();
                        newStyle.setLink(xpp.getText());
                        mapIconStyles.add(newStyle);
                    }
                    if(tagName.equals("Placemark")){
                        // Create a placemark
                        MapItem newItem = new MapItem();
                        // Get the placemark name
                        while(xpp.getName() == null || !xpp.getName().equals("name")){
                            xpp.next();
                        }
                        xpp.next();
                        newItem.setName(xpp.getText());
                        // Get the description
                        while(xpp.getName() == null || !xpp.getName().equals("description")){
                            xpp.next();
                        }
                        xpp.next();
                        newItem.setType(xpp.getText());
                        // Get the coordinates
                        while(xpp.getName() == null || !xpp.getName().equals("coordinates")){
                            xpp.next();
                        }
                        xpp.next();
                        String locationString = xpp.getText();
                        String[] splitLocation = locationString.split(",");
                        LatLng markLocation = new LatLng(Double.parseDouble(splitLocation[1]), Double.parseDouble(splitLocation[0]));
                        newItem.setLocation(markLocation);
                        mapItems.add(newItem);
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }


    // Getters and Setters
    public static boolean isMapLoaded(){
        return mapLoaded;
    }

    public static boolean isIconsLoaded() {
        return iconsLoaded;
    }

    public static boolean fullyLoaded(){
        return isIconsLoaded() && isMapLoaded();
    }

    public static ArrayList<MapItem> getMapItems(){
        return mapItems;
    }

    public static ArrayList<MapIconStyle> getIconStyles(){
        return mapIconStyles;
    }

    public static String getMapString(){
        return mapString;
    }

    public static void setSongsString(String songsString){
        CurrentMap.songsString = songsString;
    }

    public static void setWordsString(String wordsString){
        CurrentMap.wordsString = wordsString;
    }

    public static String getWords(){
        return wordsString;
    }


}
