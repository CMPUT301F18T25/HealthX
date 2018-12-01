package com.cmput301f18t25.healthx;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

public class ViewCurrentRecord extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap myMap;
    MapFragment mapFragment;
    private double longitude;
    private double latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_a_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        Bundle bundle = null;
        Record theRecord  = (Record) this.getIntent().getSerializableExtra("Record");
//        String title = bundle.getString("Title");
//        String comment = bundle.getString("Comment");
//        String date = bundle.getString("Date");
        String title = theRecord.getTitle();
        String date = theRecord.getDate();
        String comment = theRecord.getComment();
        longitude = theRecord.getLongitude();
        latitude = theRecord.getLatitude();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.RecordMap);
        mapFragment.getMapAsync(this);


//        ElasticSearchRecordController.GetRecordsTask getRecordTask = new ElasticSearchRecordController.GetRecordsTask();
//        Record record = null;
//        try {
//            record = getRecordTask.execute(title,comment,date).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//

        TextView rtitle = findViewById(R.id.record_title);
        rtitle.setText(title);
        TextView rtime = findViewById(R.id.recordTimestamp);
        rtime.setText(date);
        TextView rcomment = findViewById(R.id.record_comment);
        rcomment.setText(comment);
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

}
