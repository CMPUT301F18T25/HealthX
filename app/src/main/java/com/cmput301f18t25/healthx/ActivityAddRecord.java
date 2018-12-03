/*
 * Class Name: ActivityAddRecord
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */


package com.cmput301f18t25.healthx;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ImageView;

import android.widget.Toast;

import com.google.common.collect.ArrayTable;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.cmput301f18t25.healthx.PermissionRequest.verifyPermission;

/**
 * This is the activity that allows the user to add a record, with geo-location and photos.
 *
 * @author Dhruba
 * @author Ivan
 * @author Aida
 * @author Ajay
 * @author Cecilia
 * @version 1.0
 *
 */
public class ActivityAddRecord extends AppCompatActivity {

    ArrayList<String> imageURIs;
    LocationListener listener;
    LocationManager lm;
    Double longitude;
    Double latitude;
    String problemID;
    boolean isDoctor;
    Button  geoloc;
    int position;
    Uri imageFileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private ProblemList mProblemList = ProblemList.getInstance();
    private OfflineBehaviour offlineBehaviour = OfflineBehaviour.getInstance();
    private OfflineSave save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        save = new OfflineSave(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = this.getIntent().getExtras();
        problemID = bundle.getString("ProblemID");
        isDoctor = bundle.getBoolean("isDoctor");
        position = bundle.getInt("Position");

        imageURIs = new ArrayList<String>(10);
        setGeoLocation();
        geoloc = (Button) findViewById(R.id.record_geolocation);
        geoloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGeoLocation();

            }
        });

    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     *
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item text view to switch to add patient by code
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            Intent intent = new Intent();
            setResult(10,intent);
            finish();
        }
        if (id == R.id.save_button) {

            EditText title_textView = findViewById(R.id.record_title);
            EditText comment_textView = findViewById(R.id.record_comment);

            DatePicker recordDate_T = findViewById(R.id.recordDate);

            Date selected = new Date(recordDate_T.getYear() - 1900,
                    recordDate_T.getMonth(), recordDate_T.getDayOfMonth());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String recordDate = format.format(selected);
            String recordTitle = title_textView.getText().toString();
            String recordComment = comment_textView.getText().toString();

            setGeoLocation();

            Record newRecord = new Record(recordTitle, recordComment, latitude, longitude, imageURIs,recordDate, problemID);
            newRecord.setCPComment(isDoctor);
            mProblemList.addRecord(position,newRecord);
            save.saveRecordsToProblem(mProblemList.getElementByIndex(position));
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (null == activeNetwork) {
                offlineBehaviour.addItem(newRecord, "ADD");
                Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(10,intent);
                finish();

            } else {
                ElasticSearchRecordController.AddRecordTask addRecordTask = new ElasticSearchRecordController.AddRecordTask();
                addRecordTask.execute(newRecord);

                try {
                    Thread.sleep(1000);                 //1000 milliseconds is one second.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                Intent intent = new Intent();
                setResult(10,intent);
                finish();
            }

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ImageView imagePhoto = findViewById(R.id.view_record_photo);
                imagePhoto.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
                imageURIs.add(imageFileUri.getPath());
            }else{
                Log.d("UWW", "Rip");
            }
        }
    }

    public void addPhoto(View view){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }
      
        try {
            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
            m.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        verifyPermission(this);

        String imageFilePath = String.valueOf(System.currentTimeMillis()) + ".jpg";
        File imageFile = new File(folder,imageFilePath);
        imageFileUri = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    public void setGeoLocation() {
        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            }
        }
    }

    private void initializeLocationManager() {
        if (lm == null) {

            lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        }
    }




}
