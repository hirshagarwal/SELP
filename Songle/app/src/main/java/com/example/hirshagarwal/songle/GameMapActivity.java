package com.example.hirshagarwal.songle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.CountDownTimer;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;

import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

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
    private KmlLayer currentKml;
    static String mapString;
    private RecyclerView wordList;
    private LyricsAdapter mAdapter;
    private List<LyricItem> songList = new ArrayList<>();



    // Hyperparameters
    private static int UPDATE_INTERVAL = 5000; // ms - How frequently app requests updates - Counts against battery consumption for app
    private static int FASEST_INTERVAL = 3000; // ms - How often app will receive updates if it is requested by other apps

    // Map drop variables
    int map2Time;
    boolean bonusActive = false;


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

        // Setup the song list on the bottom sheet
        wordList = findViewById(R.id.words_recycler);
        mAdapter = new LyricsAdapter(songList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        wordList.setLayoutManager(mLayoutManager);
        wordList.setItemAnimator(new DefaultItemAnimator());
        wordList.setAdapter(mAdapter);

        CurrentMap.setGameMapActivity(this);

        try {
            // Output the song name
            Log.d("Song", CurrentMap.getCurrentSong().getTitle());
            // Setup the timer
            createTimer();
            //TODO: Create the bonus item drop times
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void createTimer(){

        // Randomly Generate Times for Bonus Drop
        map2Time = (int)(Math.random()*5 + 20);

        Log.d("Upgrade", "Time to upgrade: " + map2Time);

        new CountDownTimer(30*60*1000, 1000){
            public void onTick(long millisUntilFinished){
                bottomSheetHeader.setText("Time Remaining: " + (int)millisUntilFinished/(60*1000) + ":" + (millisUntilFinished/1000)%60);
//                CurrentMap.setSolveTime((int)millisUntilFinished/(60*1000));
                CurrentMap.setSolveTimeAccurate(millisUntilFinished);
                if (((millisUntilFinished/(60*1000) == map2Time && !bonusActive)) && CurrentMap.getMapNumber()<5){
                    // Add upgrade item
                    Log.d("Upgrade", "Map2");
                    double lowLat = 55.942617;
                    double highLat = 55.946233;
                    double lowLon = -3.184319;
                    double highLon = -3.192473;
                    double lat = (Math.random() * (highLat - lowLat)) + lowLat;
                    double lon = (Math.random() * (highLon - lowLon)) + lowLon;
                    Log.d("Latitude", lat+"");
                    Log.d("Longitude", lon+"");
                    LatLng bonusLoc = new LatLng(lat, lon);
                    mMap.addMarker(new MarkerOptions().position(bonusLoc).title("Upgrade"));
                    bonusActive = true;
                    CurrentMap.incrementMapNumber();
                    new DownloadMap().execute(CurrentMap.getMapUrl());
                    map2Time -= 5;
                }
            }
            public void onFinish(){
                //TODO: Trigger out of time
                Intent i = new Intent(getApplicationContext(), LoseActivity.class);
                startActivity(i);
            }
        }.start();
    }

    // Callback for when the map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker){
                markerClickAction(marker);
                return false;
            }
        });
        mapString = CurrentMap.getMapString();
        // Add a marker in Sydney and move the camera
        LatLng edinburgh = new LatLng(55.94, -3.187);
//        mMap.addMarker(new MarkerOptions().position(edinburgh).title("Marker in Edinburgh"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edinburgh, 16));
        try{
            InputStream stream = new ByteArrayInputStream(mapString.getBytes(StandardCharsets.UTF_8.name()));
            currentKml = new KmlLayer(mMap, stream, getApplicationContext());
            currentKml.addLayerToMap();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (org.xmlpull.v1.XmlPullParserException e){
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        // Add the location to the map UI
        updateLocationUI();
        // Initialize the bottom sheet after the map is ready
        initViews();

        // Setup FAB Listener
        FloatingActionButton fab = findViewById(R.id.guess_fab);
        fab.setOnClickListener(guessFab);
    }

    private void redrawMap(){
        mapString = CurrentMap.getMapString();

        try{
            InputStream stream = new ByteArrayInputStream(mapString.getBytes(StandardCharsets.UTF_8.name()));
            mMap.clear();
            currentKml = new KmlLayer(mMap, stream, getApplicationContext());
            currentKml.addLayerToMap();
            bonusActive = false;
        } catch (IOException e){
            e.printStackTrace();
        } catch (org.xmlpull.v1.XmlPullParserException e){
            e.printStackTrace();
        }
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
        // TODO: Reactive camera follow
        // Have the camera follow the user
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));
    }

    // Request Location Updates Method
    private void requestLocationUpdates(){
        // Check permission and call the location services
        if(checkPermission()){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    public boolean markerClickAction(Marker marker){
        Log.d("Marker Click", marker.getTitle());
        // Check if the marker is an upgrade item
        if(marker.getTitle().equals("Upgrade")){
            marker.remove();
            redrawMap();
            return true;
        }
        String[] splitTitle = marker.getTitle().split(":");
        int lineNumber = Integer.parseInt(splitTitle[0]) - 1; // Subtract one since the line numbers are 1 indexed
        int wordNumber = Integer.parseInt(splitTitle[1]) - 1; // Again to account for 1 indexing
        // Calculate Distance
        float[] results = new float[]{0, 0, 0};
        LatLng markerPos = marker.getPosition();
        if(mLastKnownLocation == null){
            // TODO: Stop markers from showing anything
            Toast t = Toast.makeText(getApplicationContext(), "Location Not Found", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }
        Location.distanceBetween(markerPos.latitude, markerPos.longitude, mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), results);
        Log.d("Distance", results[0] + "");
        if(results[0] < 35 || true){ // TODO: Remove always true
            String words = CurrentMap.getWords();
            String[] lines = words.split(System.getProperty("line.separator"));
            String[] lineWords = lines[lineNumber].split("\t")[1].split(" ");
            String foundWord = lineWords[wordNumber];
            // Get bottom sheet text
            String placemarkStyle = CurrentMap.getPlacemarkStyle(marker.getTitle());
            int numStyles = CurrentMap.getIconStyles().size();
            String bitmapLink = CurrentMap.getIconStyles().get(0).getLink();
            Bitmap bmp = CurrentMap.getIconStyles().get(0).getImage();
            Log.d("Icon", placemarkStyle);
            for (int i=0; i<numStyles; i++){
                if(CurrentMap.getIconStyles().get(i).getName().equals(placemarkStyle)){
                    bitmapLink = CurrentMap.getIconStyles().get(i).getLink();
                    bmp = CurrentMap.getIconStyles().get(i).getImage();
                }
            }
            Log.d("Icon", bitmapLink);
            // Add word to bottom sheet
            LyricItem newLyric = new LyricItem();
            foundWord = foundWord.substring(0, 1).toUpperCase() + foundWord.substring(1);
            newLyric.setWord(foundWord);
            Log.d("Word", foundWord);
            newLyric.setIcon(bmp);
            songList.add(newLyric);
            System.out.println("Last Song: " + songList.get(songList.size()-1).getWord());
            marker.remove();
            mAdapter.notifyDataSetChanged();
        }
        return true;
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
