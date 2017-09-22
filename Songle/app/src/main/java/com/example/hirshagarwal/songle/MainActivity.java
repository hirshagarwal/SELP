package com.example.hirshagarwal.songle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Fields
    private boolean locationPermissionStatus = false;
    // Define Fine Location Request Code
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1337;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Location Permissions
        getLocationPermissions();

    }

    private void getLocationPermissions(){
        // Request location permissions if they are not available
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationPermissionStatus = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    // Start game method
    public void gameStart(View view){
        Intent intent = new Intent(this, GameMapActivity.class);
        startActivity(intent);
    }

    // Settings
    public void settingsStart(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
