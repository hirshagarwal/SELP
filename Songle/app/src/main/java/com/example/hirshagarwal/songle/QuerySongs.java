package com.example.hirshagarwal.songle;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Hirsh Agarwal on 12/3/2017.
 */

public class QuerySongs extends AsyncTask<URL, Integer, String>{

    GuessSongActivity parentActivity;
    private HttpURLConnection httpConnection;

    protected String doInBackground(URL... urls){
        StringBuilder sb = new StringBuilder();
        try{
            httpConnection = (HttpURLConnection) urls[0].openConnection();
            httpConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }

        } catch (java.io.IOException e){
            e.printStackTrace();
        }

        String content = sb.toString();
        return content;
    }

    protected void onPostExecute(String data){
        try {
            JSONObject jsonObj = new JSONObject(data);
            JSONArray tracks = jsonObj.getJSONObject("results").getJSONObject("trackmatches").getJSONArray("track");
            int numResults = tracks.length();
            ArrayList<Song> songList = new ArrayList<Song>();
            parentActivity.clearSongs();
            for(int i=0; i<numResults; i++){
                // Build a song object
                Song newSong = new Song();
                // Get the parameters from the object
                JSONObject currentSongJSON = tracks.getJSONObject(i);
                String songTitle = currentSongJSON.get("name").toString();
                String songArtist = currentSongJSON.get("artist").toString();
                newSong.setArtist(songArtist);
                newSong.setTitle(songTitle);
                songList.add(newSong);
                parentActivity.addSong(newSong);
            }

            parentActivity.updateSongList();
        } catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }
}
