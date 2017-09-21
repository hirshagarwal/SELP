package com.example.hirshagarwal.songle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Start game method
    public void gameStart(View view){
        Intent intent = new Intent(this, GameMapActivity.class);
        startActivity(intent);
    }

}
