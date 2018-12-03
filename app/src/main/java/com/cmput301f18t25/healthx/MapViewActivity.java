/*
 * Class Name: MapViewActivity
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * This is the activity that uses google map to show the location of the record.
 *
 * @author Dhruba
 * @version 1.0
 *
 */

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap myMap;
    MapFragment mapFragment;
    ArrayList<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        Intent intent = getIntent();
        records = (ArrayList<Record>) intent.getSerializableExtra("Records");


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {

        }
        // Sets the mapview such that it contains all locations markers on the google map
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        for (Record r : records) {
            LatLng placeLocation = new LatLng(r.getLatitude(), r.getLongitude());
            Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation));
            placeMarker.setTitle(r.getTitle());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);

        }
//


    }
}
