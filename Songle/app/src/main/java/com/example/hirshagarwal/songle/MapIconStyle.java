package com.example.hirshagarwal.songle;

import android.graphics.Bitmap;

/**
 * Created by Hirsh Agarwal on 9/27/2017.
 */

public class MapIconStyle {
    public String name;
    public double scale;
    public String link;
    private Bitmap image;
    public MapIconStyle(){

    }

    public MapIconStyle(String name, double scale, String link){
        this.name = name;
        this.scale = scale;
        this.link = link;
    }

    // Getters and Setters
    public void setName(String name){
        this.name = name;
    }
    public void setScale(double scale){
        this.scale = scale;
    }
    public void setLink(String link){
        this.link = link;
    }
    public void setImage(Bitmap image){
        this.image = image;
    }
    public String getName(){
        return name;
    }
    public String getLink(){
        return link;
    }
    public double getScale(){
        return scale;
    }
    public Bitmap getImage(){
        return image;
    }

}
