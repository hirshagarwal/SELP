package com.example.hirshagarwal.songle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GuessSongActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView queryInput;
    private ArrayList<Song> songList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_song);

        // Set Input
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Get the recycler view
        mRecyclerView = findViewById(R.id.recyclerView_song_list);
        mRecyclerView.setHasFixedSize(true);

        // User a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // fzrjYMcXldotXseGktwTsGQGEJvgAoeMuyfXjvoH
//        String[] list = new String[]{"Song 1", "Song 2"};
        // Setup Adapter
        mAdapter = new SongListAdapter(getApplicationContext(), songList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        queryInput = findViewById(R.id.editText_song_guess);
        // Query the databse when a user starts typing
        queryInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void textChanged(){
        QuerySongs songListRequester = new QuerySongs();
        songListRequester.parentActivity = this;
        String queryText = queryInput.getText().toString();
        String requestString = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + queryText + "&api_key=af67010c30ad7f1567416b9060b181f5&format=json";
        URL requestUrl;
        try{
            requestUrl = new URL(requestString);
            songListRequester.execute(requestUrl);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    public void clearSongs(){
        songList.clear();
    }

    public void addSong(Song s){
        songList.add(s);
    }

    public void updateSongList(){
        mAdapter.notifyDataSetChanged();
    }
}
