package com.example.hirshagarwal.songle;

import android.graphics.Bitmap;

/**
 * Created by Hirsh Agarwal on 12/1/2017.
 */

public class LyricItem {

    private String word;
    private Bitmap icon;

    public void setWord(String word){
        this.word = word;
    }

    public void setIcon(Bitmap icon){
        this.icon = icon;
    }

    public String getWord(){
        return word;
    }

    public Bitmap getIcon(){
        return icon;
    }


}
