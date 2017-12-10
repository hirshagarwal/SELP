package com.example.hirshagarwal.songle;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hirsh Agarwal on 12/8/2017.
 */

public class GameMapActivityUnitTest {
    @Test
    public void generate_bonusLocation() throws Exception {
        GameMapActivity g = new GameMapActivity();
        LatLng location = g.bonusLocation();
        // Check Latitude
        assertTrue(location.latitude < 55.946233);
        assertTrue(location.latitude > 55.942617);
        // Check Longitude
        assertTrue(location.longitude < -3.184319);
        assertTrue(location.longitude > -3.192473);
    }

    public void failingTest() throws Exception{
        assertTrue(false);
    }


}
