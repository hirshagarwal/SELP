package com.example.hirshagarwal.songle;

import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Hirsh Agarwal on 12/10/2017.
 */

public class DownloadTimes extends AsyncTask<URL, Integer, String>{

    public String doInBackground(URL... urls){
        URL url = urls[0];
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            int status = httpURLConnection.getResponseCode();

            switch (status){
                case 200:
                    Log.d("Connection", "Response 200");
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null){
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
            }


        } catch (java.io.IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public void onPostExecute(String data){
        // Pase JSON return data
        Log.d("Return JSON", data);

    }

}
