package com.example.hirshagarwal.songle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // Settings Icon
    ImageView settingsIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the icons
         settingsIcon = (ImageView) findViewById(R.id.main_page_settings_icon);

        // On touch animation for settings icon
        settingsIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.animation_settings_touch));
                return false;
            }
        });
    }


    // Start game method
    public void gameStart(View view){
        Intent intent = new Intent(this, GameMapActivity.class);
        startActivity(intent);
    }

    // Settings
    public void settingsStart(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
//        view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.animation_settings_touch));
        startActivity(intent);
    }

}
