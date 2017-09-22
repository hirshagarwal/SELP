package com.example.hirshagarwal.songle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.design.widget.FloatingActionButton;

public class GameMapActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    // Bottom Sheet Variables
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView bottomSheetHeader;

    // Fields
    private boolean mLocationPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Check location permissions
        mLocationPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng edinburgh = new LatLng(55.953252, -3.188267);
        mMap.addMarker(new MarkerOptions().position(edinburgh).title("Marker in Edinburgh"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(edinburgh));

        updateLocation();

        // Initialize the bottom sheet after the map is ready
        initViews();

        // Setup FAB Listener
        FloatingActionButton fab = findViewById(R.id.guess_fab);
        fab.setOnClickListener(guessFab);
    }

    private View.OnClickListener guessFab = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), GuessSongActivity.class);
            startActivity(intent);
        }
    };

    private void updateLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            Log.e("Update Location", "Didn't manage to update location");
        }
    }

    private void getDeviceLocation(){
        if (mLocationPermission){
//            Task locationResult =
        }
    }

    private void initViews(){
        // Set all of the bottom sheet variables
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.content_bottom_sheet_game_map));
        bottomSheetHeader = (TextView) findViewById(R.id.bottom_sheet_header);
    }

    private void initListeners(){
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback(){
            public void onStateChanged(View bottomSheet, int newState){
                // Do stuff on bottom sheet state changed
            }

            public void onSlide(View bottomSheet, float slideOffset){
                // If the bottom sheet is sliding?
            }
        });
    }
}
