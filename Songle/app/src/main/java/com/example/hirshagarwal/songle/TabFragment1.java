package com.example.hirshagarwal.songle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        // Load preferences
        SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE);
        Set<String> solveTimes = pref.getStringSet(getString(R.string.scores), new HashSet<String>());
        Float highScore = pref.getFloat(getString(R.string.highScore), 0);
        if(solveTimes.size() != 0){
            Iterator solveTimesIterator = solveTimes.iterator();
            double totalTime = 0;
            while(solveTimesIterator.hasNext()){
                totalTime += Double.parseDouble(solveTimesIterator.next().toString());
            }
            totalTime /= solveTimes.size();
            int mins = (int)totalTime/(1000*60);
            int seconds = (int)totalTime/1000%60;
            averageTime.setText(mins + ":" + seconds);
        } else {
            averageTime.setText("None");
        }

        if(highScore != 0){
            double  highScoreDouble = highScore;
            int mins = (int)highScoreDouble/(1000*60);
            int seconds = (int)highScoreDouble/1000%60;
            bestTime.setText(mins + ":" + seconds);
        } else {
            bestTime.setText("None");
        }




        return view;
    }

}
