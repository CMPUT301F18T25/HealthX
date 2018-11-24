package com.cmput301f18t25.healthx;

import android.content.Intent;
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

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap myMap;
    MapFragment mapFragment;
    ArrayList<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        records = (ArrayList<Record>) args.getSerializable("RecordList");


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
        //Edit the following as per you needs
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        for (Record r : records) {
            Log.d("IVANLIM",  Double.toString(r.getLatitude()) + Double.toString(r.getLongitude()));
            LatLng placeLocation = new LatLng(r.getLatitude(), r.getLongitude());
            Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation));
            placeMarker.setTitle(r.getTitle());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);

        }
//
//        LatLng placeLocation = new LatLng(53.522834950000004, -113.5314327);
//        Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);


    }
}
