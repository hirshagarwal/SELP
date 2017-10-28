package com.example.hirshagarwal.songle;

import android.os.AsyncTask;

import java.io.IOException;
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
        try{
            this.requestUrl = urls[0];
            httpConnection = (HttpURLConnection) requestUrl.openConnection();
            
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
