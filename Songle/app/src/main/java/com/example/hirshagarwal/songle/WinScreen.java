package com.example.hirshagarwal.songle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class WinScreen extends AppCompatActivity {

    Button scoresButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        // Store the new high score
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        double highScore = sharedPref.getFloat(getString(R.string.highScore), 0);
        Log.d("Preferences", "High Score: " + highScore);
        if(highScore < CurrentMap.getSolveTimeAccurate()){
            Log.d("Preferences", "New High Score");
            editor.putFloat(getString(R.string.highScore), (float)CurrentMap.getSolveTimeAccurate());
        }

        // Add the score to the score list
        Set<String> scoreSet = sharedPref.getStringSet(getString(R.string.scores), null);
        if(scoreSet == null){
            scoreSet = new HashSet<String>();
        }
//        Log.d("Scores List", scoreSet.size() + "");
        scoreSet.add(CurrentMap.getSolveTimeAccurate()+"");
        editor.putStringSet(getString(R.string.scores), scoreSet);
//        Log.d("Score List Updated", scoreSet.size() + "");

        // Add the song to the song list
        Set<String> songSet = sharedPref.getStringSet(getString(R.string.songsPlayed), null);
        if(songSet == null){
            songSet = new HashSet<String>();
        }

        Log.d("Preferences", songSet.size() + "");
        songSet.add(CurrentMap.getCurrentSong().getTitle());
        Log.d("Preferences Updated", songSet.size()  + "");
        editor.putStringSet(getString(R.string.songsPlayed), songSet);
        boolean status = editor.commit();
//        Log.d("Preference Written", status + "");


        scoresButton = findViewById(R.id.viewScoresButton);
        // Set the score button listener
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ScoresActivity.class);
                view.getContext().startActivity(i);
            }
        });

        TextView solveTime = findViewById(R.id.solveTimeText);
        solveTime.setText("Time: " + (int)CurrentMap.getSolveTimeAccurate()/(60*1000) + " Minutes");

    }
}
