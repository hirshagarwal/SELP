package com.example.hirshagarwal.songle;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hirsh Agarwal on 10/26/2017.
 */

public class DownloadWords extends AsyncTask<URL, Integer, String>{

    private URL requestUrl;
    private HttpURLConnection httpConnection;
    private static final String ns = null;

    @Override
    protected String doInBackground(URL... urls) {
        StringBuilder sb = new StringBuilder();
        try{
            this.requestUrl = urls[0];
            httpConnection = (HttpURLConnection) requestUrl.openConnection();
            // Connect to the specified URL
            httpConnection.connect();
            // Build a buffered reader
            // TODO: Comment this xml input system
            BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }
        } catch (java.io.IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    protected void onPostExecute(String data){
        
    }

}
