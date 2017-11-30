package com.example.hirshagarwal.songle;


/**
 * Created by Hirsh Agarwal on 11/30/2017.
 */

public class Song {

    private String Number;
    private String Artist;
    private String Title;
    private String Link;

    public void setNumber(String Number){
        this.Number = Number;
    }

    public void setArtist(String Artist){
        this.Artist = Artist;
    }

    public void setTitle(String title){
        this.Title = Title;
    }

    public void setLink(String link){
        this.Link = Link;
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
