package com.example.hirshagarwal.songle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Set;

public class ScoresActivity extends AppCompatActivity {

    TextView gamesPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Me"));
        tabLayout.addTab(tabLayout.newTab().setText("Everyone"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));

        gamesPlayed = findViewById(R.id.numGamesPlayed);

        // Calculate the number of games played
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE);
        Set<String> scores = sharedPref.getStringSet(getString(R.string.scores), null);
        if(scores == null){
            gamesPlayed.setText("0");
        } else {
            int numGamesPlayed = scores.size();
            gamesPlayed.setText(numGamesPlayed + "");
        }

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new ScoresAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());
            }
            public void onTabUnselected(TabLayout.Tab tab){

            }

            public void onTabReselected(TabLayout.Tab tab){

            }
        });

    }

    public void returnHome(View view){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
