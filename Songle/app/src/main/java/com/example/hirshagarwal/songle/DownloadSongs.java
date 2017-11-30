package com.example.hirshagarwal.songle;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hirsh Agarwal on 11/29/2017.
 */

public class DownloadSongs extends AsyncTask<Void, Integer, String>{

    private HttpURLConnection httpConnection;

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected String doInBackground(Void... params){
        // Create the request URL
        URL url;
        StringBuilder sb = new StringBuilder();
        try{
            url = new URL("http://www.inf.ed.ac.uk/teaching/courses/selp/data/songs/songs.xml");
            // Create a connector
            httpConnection = (HttpURLConnection) url.openConnection();
            // Connect to the URL
            httpConnection.connect();
            // Build a buffered reader
            BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (java.io.IOException e){
            e.printStackTrace();
        }

        return sb.toString();
    }

    protected void onPostExecute(String data){
        CurrentMap.setSongsString(data);
        CurrentMap.loadMap();
    }

}
