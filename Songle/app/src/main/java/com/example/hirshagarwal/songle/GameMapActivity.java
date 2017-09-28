package com.example.hirshagarwal.songle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.design.widget.FloatingActionButton;

public class GameMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
//    private FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    private GoogleApiClient googleApiClient;
    private CoordinatorLayout mapLayout;

    // Bottom Sheet Variables
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView bottomSheetHeader;

    // Fields
    private Location mLastKnownLocation;
    private LocationRequest locationRequest;

    // Hyperparameters
    private static int UPDATE_INTERVAL = 5000; // ms - How frequently app requests updates - Counts against battery consumption for app
    private static int FASEST_INTERVAL = 3000; // ms - How often app will receive updates if it is requested by other apps


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get the map layout
        mapLayout = findViewById(R.id.layout_map);

        // Build the GoogleAPI Client
        googleApiClient = new GoogleApiClient.Builder(this)
        .addApi(LocationServices.API)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build();

        // Initialize location request with accuracy and frequency for GPS updates
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    // Callback for when the map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng edinburgh = new LatLng(55.953252, -3.188267);
        mMap.addMarker(new MarkerOptions().position(edinburgh).title("Marker in Edinburgh"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edinburgh, 10));
        int markersToAdd = CurrentMap.getMapItems().size();
        Log.d("Markers", markersToAdd+"");
        for(int i=0; i<markersToAdd; i++){
            Log.d("Markers", "Adding Markers");
            LatLng markerPos = CurrentMap.getMapItems().get(i).getLocation();
            if(CurrentMap.getMapItems().get(i).getType().equalsIgnoreCase("boring")){
                mMap.addMarker(new MarkerOptions().position(markerPos).title("Marker in Edinburgh").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            } else {
                mMap.addMarker(new MarkerOptions().position(markerPos).title("Marker in Edinburgh"));
            }
        }
        // Add the location to the map UI
        updateLocationUI();
        // Initialize the bottom sheet after the map is ready
        initViews();

        // Setup FAB Listener
        FloatingActionButton fab = findViewById(R.id.guess_fab);
        fab.setOnClickListener(guessFab);
    }

    // -- Interface Methods --
    /*
    * All of the methods for location requests
    */
    // Connection Callback
    public void onConnected(Bundle extras){
        requestLocationUpdates();
    }
    public void onConnectionSuspended(int id){
        Log.i("Location", "Connection Suspended");
    }
    // Connection Failed
    public void onConnectionFailed(ConnectionResult r){
        Log.e("Location", "Connection Failed");
    }
    // Location Listener
    public void onLocationChanged(Location currentLocation){
        // Set the last location
        mLastKnownLocation = currentLocation;
        // Have the camera follow the user
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));
    }

    // Request Location Updates Method
    private void requestLocationUpdates(){
        // Check permission and call the location services
        if(checkPermission()){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    // Override start and stop methods
    /*
     * These methods are here to ensure that we don't stay connected to services that we aren't using while the app is being multitasked or hidden for some reason
     * If the app is stopped or started we can connect or disconnect from the service
     * However if it's just paused or resumed we might want to just stop requesting updates
     */
    protected void onStart(){
        super.onStart();
        googleApiClient.connect();
    }
    protected void onStop(){
        super.onStop();
        googleApiClient.disconnect();
    }
    protected void onPause(){
        super.onPause();
        // Unsubscribe from the location updates
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }
    protected void onResume(){
        super.onResume();
        if(googleApiClient.isConnected()){
            requestLocationUpdates();
        }
        // If we're not already connected it will end up at the onConnected method when it is anyway
    }

    public boolean checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    /*
    * Other methods
     */

    // Guess FAB Action
    private View.OnClickListener guessFab = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), GuessSongActivity.class);
            startActivity(intent);
        }
    };

    // Update the UI with a current location button
    private void updateLocationUI(){
        Log.d("Updating Location UI", "Updating Location UI");
        if(checkPermission()){
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            Log.e("Update Location", "Didn't manage to update location");
        }
    }

    private void initViews(){
        // Set all of the bottom sheet variables
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.content_bottom_sheet_game_map));
        bottomSheetHeader = findViewById(R.id.bottom_sheet_header);
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
