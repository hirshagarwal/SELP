package com.example.hirshagarwal.songle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Hirsh Agarwal on 10/5/2017.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>{
    private ArrayList<Song> songList;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public CardView mCardView;

        public ViewHolder(View v){
            super(v);
            mTextView = v.findViewById(R.id.info_text);
            mCardView = v.findViewById(R.id.card_view);
        }
    }

    public SongListAdapter(Context context, ArrayList<Song> songList){
        this.songList = songList;
        this.context = context;
    }

    public SongListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songGuess = songList.get(position).getTitle();
                String songTrue = CurrentMap.getCurrentSong().getTitle();
                if(songGuess.toLowerCase().equals(songTrue.toLowerCase())){
                    Log.d("Song", "Correct");
                    Intent i = new Intent(context, WinScreen.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                    ((GuessSongActivity)context).finish();
                    CurrentMap.getGameMap().finish();
                } else {
                    Toast incorrect = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
                    incorrect.show();
                }
            }
        });
        holder.mTextView.setText(songList.get(position).getTitle());
    }

    public int getItemCount(){
        return songList.size();
    }

}
