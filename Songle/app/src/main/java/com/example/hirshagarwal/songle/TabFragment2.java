package com.example.hirshagarwal.songle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hirsh Agarwal on 12/7/2017.
 */

public class TabFragment2 extends Fragment {

    TextView topScore;
    TextView averageScore;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.tab_fragment_2, container, false);

        topScore = v.findViewById(R.id.bestTime);
        averageScore = v.findViewById(R.id.averageTime);

        // Download all of the global data
        DownloadTimes dt = new DownloadTimes();
        dt.setCallbackFragment(this);
        try{
            URL url = new URL("http://52.40.72.100/Songle/downloadData.php");
            dt.execute(url);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return v;
    }

    public void updateScores(int top, int average){
        // Check times
        if(average == 0){
            return;
        }

        // If average is not 0
        top = (30*1000*60) - top;
        int minutes = top/(1000*60);
        int seconds = top/1000%60;
        topScore.setText(minutes+":"+seconds);

        // Set the average score
        average = (30*1000*60) - average;
        minutes = average/(1000*60);
        seconds = average/1000%60;
        averageScore.setText(minutes+":"+seconds);
    }
}
