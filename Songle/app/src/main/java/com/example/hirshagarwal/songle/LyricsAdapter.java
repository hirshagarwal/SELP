package com.example.hirshagarwal.songle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Hirsh Agarwal on 12/1/2017.
 */

public class LyricsAdapter extends RecyclerView.Adapter<LyricsAdapter.MyViewHolder>{

    private List<LyricItem> lyricsList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView icon;

        public MyViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.word);
            icon = view.findViewById(R.id.icon);
        }

    }

    public LyricsAdapter(List<LyricItem> lyricList){
        this.lyricsList = lyricList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyric_recycler_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position){
        LyricItem lyric = lyricsList.get(position);
        holder.title.setText(lyric.getWord());
        Bitmap mutableBitmap = lyric.getIcon().copy(Bitmap.Config.ARGB_8888, true);
        mutableBitmap = Bitmap.createScaledBitmap(mutableBitmap, 90, 90, false);
        Canvas c = new Canvas(mutableBitmap);
        holder.icon.setImageBitmap(mutableBitmap);
//        holder.icon.draw(c);
    }

    public int getItemCount(){
        return lyricsList.size();
    }

}
