package com.example.hirshagarwal.songle;

import android.net.Network;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Hirsh Agarwal on 9/25/2017.
 */

public class NetworkHandler extends AsyncTask<String, Void, String> {

    private String requestUrl = "";

    public NetworkHandler(String requestUrl){
        this.requestUrl = requestUrl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Ensure that everything is ready for the network request
    }

    // Interface Methods
    protected String doInBackground(String... urls){
        return "";
    }

    protected void onPostExecute(String data){
        Log.d("Maps Request", "Request Complete");
    }
}
