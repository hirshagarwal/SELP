package com.example.hirshagarwal.songle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    // Define Fine Location Request Code
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1337;
    private final int PERMISSIONS_REQUEST_ACCESS_INTERNET = 1338;

    Switch internetSwitch;
    Switch locationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get the switch items
        internetSwitch = findViewById(R.id.internetSwitch);
        locationSwitch = findViewById(R.id.locationSwitch);

        // Set the switch positions
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            internetSwitch.setChecked(true);
        } else {
            internetSwitch.setChecked(false);
        }

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            internetSwitch.setChecked(true);
        } else {
            internetSwitch.setChecked(false);
        }

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getLocationPermission();
            }
        });
        internetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getInternetPermission();
            }
        });

    }

    public void getInternetPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PERMISSIONS_REQUEST_ACCESS_INTERNET);
    }

    public void getLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }
}
