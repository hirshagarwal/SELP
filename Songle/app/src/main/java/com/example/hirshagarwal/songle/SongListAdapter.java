package com.example.hirshagarwal.songle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Hirsh Agarwal on 10/5/2017.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>{
    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View v){
            super(v);
            mTextView = v.findViewById(R.id.info_text);
        }
    }

    public SongListAdapter(String[] myDataset){
        mDataset = myDataset;
    }

    public SongListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.mTextView.setText(mDataset[position]);
    }

    public int getItemCount(){
        return mDataset.length;
    }

}
