package com.example.hirshagarwal.songle;

import android.net.Network;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.google.android.gms.location.places.Places;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hirsh Agarwal on 9/25/2017.
 */

public class DownloadMap extends AsyncTask<URL, Integer, String> {

    private URL requestUrl;
    private HttpURLConnection httpConnection;
    private static final String ns = null;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Ensure that everything is ready for the network request
        // Check that URL is available
        // TODO: Implement URL verification
        // Try to convert the String to a URL


    }

    // Interface Methods
    protected String doInBackground(URL... requestUrls){
        String mapDataString = "";
        StringBuilder sb = new StringBuilder();
        try {
            this.requestUrl = requestUrls[0];
            // Create a connector
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

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // Provide the current progress
    }

    protected void onPostExecute(String data){
        // Create the XML Parser

        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
        } catch (org.xmlpull.v1.XmlPullParserException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        Log.d("Maps Request", "Request Complete");
    }


}
