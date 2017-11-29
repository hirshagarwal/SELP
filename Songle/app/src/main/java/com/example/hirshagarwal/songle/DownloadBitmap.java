package com.example.hirshagarwal.songle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hirsh Agarwal on 11/29/2017.
 */

public class DownloadBitmap extends AsyncTask<URL, Void, Bitmap>{

    private int styleIndex = 0;

    DownloadBitmap(int styleIndex){
        this.styleIndex = styleIndex;
    }

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected Bitmap doInBackground(URL... requestUrls) {
        URL url;
        url = requestUrls[0];
        Bitmap bmp;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Bitmap image){
        CurrentMap.getIconStyles().get(styleIndex).setImage(image);
    }


}
