package com.example.hirshagarwal.songle;

import android.app.Fragment;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Hirsh Agarwal on 12/10/2017.
 */

public class DownloadTimes extends AsyncTask<URL, Integer, String>{

    TabFragment2 callbackFragment;

    public String doInBackground(URL... urls){
        URL requestUrl = urls[0];

        try{
            HttpURLConnection httpUrlConnection = (HttpURLConnection) requestUrl.openConnection();

            // Generate URL parameters
            JSONObject postDataParams = new JSONObject();
            try{
                postDataParams.put("score", 0);
                HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK){
//                    Log.d("Response", "OK");
                    Log.d("Post", conn.getResponseMessage());
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";
                    while((line = in.readLine()) != null){
                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                }

            } catch (JSONException e){
                e.printStackTrace();
            } catch (java.lang.Exception e){
                e.printStackTrace();
            }


        } catch (java.io.IOException e){
            e.printStackTrace();
        }

        return "";

    }

    public void onPostExecute(String data){
        Log.d("Post Execute", data);
        // Decode JSON
        try{
            JSONObject jsonObject = new JSONObject(data);
            int topScore = jsonObject.getInt("topScore");
            int averageScore = jsonObject.getInt("averageScore");
            callbackFragment.updateScores(topScore, averageScore);
            return;

        } catch (org.json.JSONException e){
            e.printStackTrace();
        }

        callbackFragment.updateScores(0, 0);

    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public void setCallbackFragment(TabFragment2 callbackFragment){
        this.callbackFragment = callbackFragment;
    }



}
