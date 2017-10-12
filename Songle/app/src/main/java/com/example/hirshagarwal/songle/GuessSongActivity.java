package com.example.hirshagarwal.songle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

public class GuessSongActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_song);

        // Set Input
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Get the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_song_list);
        mRecyclerView.setHasFixedSize(true);

        // User a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] list = new String[]{"Song 1", "Song 2"};
        // Setup Adapter
        mAdapter = new SongListAdapter(list);
        mRecyclerView.setAdapter(mAdapter);

    }
}
