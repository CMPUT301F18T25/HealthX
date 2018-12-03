/*
 * Class Name: ViewCurrentRecord
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
public class ViewCurrentRecord extends AppCompatActivity implements Serializable,OnMapReadyCallback, View.OnClickListener {


    GoogleMap myMap;
    MapFragment mapFragment;
    private double longitude;
    private double latitude;
    Record theRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_a_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        theRecord = (Record) this.getIntent().getSerializableExtra("Record");
        String title = theRecord.getTitle();
        String date = theRecord.getDate();
        String comment = theRecord.getComment();
        final ArrayList<String> images = theRecord.getImageURIs();
        longitude = theRecord.getLongitude();

        latitude = theRecord.getLatitude();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.RecordMap);
        mapFragment.getMapAsync(this);


        TextView rtitle = findViewById(R.id.record_title);
        rtitle.setText(title);
        TextView rtime = findViewById(R.id.recordTimestamp);
        rtime.setText(date);
        TextView rcomment = findViewById(R.id.record_comment);
        rcomment.setText(comment);

        Button photos = (Button) findViewById(R.id.SeePhoto);
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No Photos for Record", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), theRecord.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ActivitySeeRecordPhotos.class);
                    intent.putExtra("Record", theRecord);
                    startActivity(intent);
                }

            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {


        myMap = googleMap;
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException ignored) {

        }
        //Edit the following as per you needs
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng placeLocation = new LatLng(latitude , longitude);
        googleMap.addMarker(new MarkerOptions().position(placeLocation));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.SeePhoto: {
                Intent intent = new Intent(getApplicationContext(),ActivitySeeRecordPhotos.class);
                intent.putExtra("Record", theRecord);
                startActivity(intent);
            }
        }
    }

}
