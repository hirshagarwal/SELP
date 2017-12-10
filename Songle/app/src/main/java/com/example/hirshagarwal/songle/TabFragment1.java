package com.example.hirshagarwal.songle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Hirsh Agarwal on 12/7/2017.
 */

public class TabFragment1 extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.tab_fragment_1, container, false);

        TextView averageTime = view.findViewById(R.id.averageTime);
        TextView bestTime = view.findViewById(R.id.bestTime);
        TextView lastTime = view.findViewById(R.id.lastTime);

        // Load preferences
        SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE);
        Set<String> solveTimes = pref.getStringSet(getString(R.string.scores), new HashSet<String>());
        double lastSolveTime = 0;
        Float highScore = pref.getFloat(getString(R.string.highScore), 0);
        if(solveTimes.size() != 0){
            Iterator solveTimesIterator = solveTimes.iterator();
            double totalTime = 0;
            while(solveTimesIterator.hasNext()){
                String currentTime = solveTimesIterator.next().toString();
                totalTime += Double.parseDouble(currentTime);
                lastSolveTime = Double.parseDouble(currentTime);
            }
            totalTime /= solveTimes.size();
            totalTime = (30*1000*60) - totalTime;
            int mins = (int)totalTime/(1000*60);
            int seconds = (int)totalTime/1000%60;
            averageTime.setText(mins + ":" + seconds);

            // Set the last solve time
            lastSolveTime = (30 * 1000 * 60) - lastSolveTime;
            mins = (int)lastSolveTime/(1000*60);
            seconds = (int)lastSolveTime/1000%60;
            lastTime.setText(mins+":"+seconds);

        } else {
            averageTime.setText("None");
            lastTime.setText("None");
        }

        if(highScore != 0){
            double  highScoreDouble = highScore;
            highScoreDouble = (30*1000*60) - highScoreDouble;
            int mins = (int)highScoreDouble/(1000*60);
            int seconds = (int)highScoreDouble/1000%60;
            bestTime.setText(mins + ":" + seconds);
        } else {
            bestTime.setText("None");
        }


        return view;
    }

}
