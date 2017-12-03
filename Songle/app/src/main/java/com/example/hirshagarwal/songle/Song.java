package com.example.hirshagarwal.songle;


import android.graphics.Bitmap;

/**
 * Created by Hirsh Agarwal on 11/30/2017.
 */

public class Song {

    private String Number;
    private String Artist;
    private String Title;
    private String Link;
    private Bitmap Image;

    public void setNumber(String Number){
        this.Number = Number;
    }

    public void setArtist(String Artist){
        this.Artist = Artist;
    }

    public void setTitle(String title){
        this.Title = title;
    }

    public void setLink(String link){
        this.Link = Link;
    }

    public void setImage(Bitmap image){
        this.Image = image;
    }

    public Bitmap getImage(){
        return Image;
    }

    public String getNumber(){
        return Number;
    }

    public String getArtist(){
        return Artist;
    }

    public String getTitle(){
        return Title;
    }

    public String getLink(){
        return Link;
    }

}
