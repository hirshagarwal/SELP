package com.example.hirshagarwal.songle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Fields
    private boolean locationPermissionStatus = false;
    private boolean internetPermissionStatus = false;
    // Define Fine Location Request Code
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1337;
    private final int PERMISSIONS_REQUEST_ACCESS_INTERNET = 1338;

    Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CurrentMap.clearMap();

        // Get the start button
        startButton = findViewById(R.id.start_game_button);
        setStartVisible(false);

        // Get Location Permissions
        getLocationPermissions();
        getNetworkPermissions();

        // Allow the current map object to access the context
        CurrentMap.setMainActivity(this);

        // Check that internet is connected
        boolean internet = isInternetAvailable();
        if (!internet){
            Toast t = Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT);
            t.show();
            return;
        }
        // If network permission was granted we probably want to initiate the map download so that it feels quick
        CurrentMap.loadData();
        Log.d("Map", "Loading the map");

    }

    public void setStartVisible(Boolean visible){
        if(visible){
            startButton.setVisibility(View.VISIBLE);
        } else {
            startButton.setVisibility(View.INVISIBLE);
        }
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    // Setup the internet permissions
    private void getNetworkPermissions(){
        // Request network permissions if they are not available
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            internetPermissionStatus = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PERMISSIONS_REQUEST_ACCESS_INTERNET);
        }
    }

    // Setup the location permissions
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

    public void leaderboardStart(View view){
        Intent i = new Intent(this, ScoresActivity.class);
        startActivity(i);
    }

    // Settings
    public void settingsStart(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
