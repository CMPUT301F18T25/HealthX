package com.cmput301f18t25.healthx;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ActivityEditRecord extends AppCompatActivity {
    Bitmap recordPhoto;
    private LocationManager locationManager;
    Location location;
    double longitude;
    double latitude;
    String title;
    String comment;
    String dateString;
    Record oldRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();

        EditText title_textView = findViewById(R.id.record_title);
        EditText comment_textView = findViewById(R.id.record_comment);
        DatePicker recordDate_T = findViewById(R.id.recordDate);


        oldRecord = (Record) bundle.getSerializable("record");
        title = oldRecord.getTitle();
        comment = oldRecord.getComment();
        dateString = oldRecord.getDate();

        title_textView.setText(title);
        comment_textView.setText(comment);
        recordDate_T.updateDate(Integer.valueOf(dateString.substring(0, 4)),
                Integer.valueOf(dateString.substring(5, 7)) - 1,
                Integer.valueOf(dateString.substring(8, 10)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // if clicked the save button,
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, ViewRecordList.class);
            startActivity(intent);
        }
        if (id == R.id.save_button) {

            EditText title_textView = findViewById(R.id.record_title);
            EditText comment_textView = findViewById(R.id.record_comment);
            DatePicker recordDate_T = findViewById(R.id.recordDate);

            Date selected = new Date(recordDate_T.getYear() - 1900,
                    recordDate_T.getMonth(), recordDate_T.getDayOfMonth());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String recordDate = format.format(selected);

            Toast.makeText(getApplicationContext(), recordDate, Toast.LENGTH_SHORT).show();


            String recordTitle = title_textView.getText().toString();
            String recordComment = comment_textView.getText().toString();
            setGeoLocation();

            // Check if app is connected to a network.
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null == activeNetwork) {
                Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
            } else {

                Record newRecord = new Record(recordTitle, recordComment, latitude, longitude, recordPhoto,recordDate);
                ElasticSearchRecordController.AddRecordTask addRecordTask = new ElasticSearchRecordController.AddRecordTask();
                addRecordTask.execute(newRecord);
                ElasticSearchRecordController.DeleteRecordTask deleteRecordTask = new ElasticSearchRecordController.DeleteRecordTask();
                deleteRecordTask.execute(oldRecord);
                Intent intent = new Intent(ActivityEditRecord.this, ViewRecordList.class);
                startActivity(intent);
            }

        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {

            ImageView imageView = findViewById(R.id.view_photo);
            byte[] byteArray = data.getByteArrayExtra("image");
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
            Drawable drawable = new BitmapDrawable(bitmapScaled);
            imageView.setImageDrawable(drawable);

            imageView.setImageBitmap(bitmap);


            recordPhoto = bitmap;

        }else{
            Toast.makeText(ActivityEditRecord.this,"Unable To Set Photo To Record",Toast.LENGTH_LONG).show();
        }
    }

    public void addPhoto(View view){

        Intent photoIntent = new Intent(this, ActivityAddPhoto.class);
        startActivityForResult(photoIntent, 1);

    }

    public void setGeoLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null){
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Log.d("SANDY 301", String.valueOf(longitude));
                Log.d("SANDY 301", String.valueOf(latitude));
            }
            else{
                Log.d("SANDY 301","NO LOCATION");
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                setGeoLocation();
                break;
        }

    }

}