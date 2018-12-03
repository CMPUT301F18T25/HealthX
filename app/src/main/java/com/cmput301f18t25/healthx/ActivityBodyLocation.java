/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityBodyLocation extends AppCompatActivity {

    String frontBodyLocation;
    String backBodyLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        final ImageView imgView = findViewById(R.id.body_image);
        final TextView textView = findViewById(R.id.body_image_textview);
        final Button button = findViewById(R.id.changeBodyViewButton);


        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if(button.getText().equals("SWITCH TO BACK VIEW")){
                    button.setText("SWITCH TO FRONT VIEW");
                }else{
                    button.setText("SWITCH TO BACK VIEW");
                }
            }
        });
        imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int x = (int) event.getX();
                int y = (int) event.getY();
                int width = imgView.getMeasuredWidth();
                int height = imgView.getMeasuredHeight();
                if (button.getText().equals("SWITCH TO BACK VIEW")) {
                    if (x > 0 && x < 0.34 * width && y > 0.35 * height && y < 0.58 * height) {
                        frontBodyLocation = "Front Left Arm";
                    } else if (x > 0.67 * width && x < width && y > 0.35 * height && y < 0.58 * height) {
                        frontBodyLocation = "Front Right Arm";
                    } else if (x > 0.277 * width && x < 0.75 * width && y > 0 && y < 0.356 * height) {
                        frontBodyLocation = "Head";
                    } else if (x > 0.34 * width && x < 0.67 * width && y > 0.356 * height && y < 0.58 * height) {
                        frontBodyLocation = "Chest";
                    } else if (x > 0.04 * width && x < 0.5 * width && y > 0.786 * height && y < 0.974 * height) {
                        frontBodyLocation = "Front Left Leg";
                    } else if (x > 0.5 * width && x < 0.973 * width && y > 0.786 * height && y < 0.974 * height) {
                        frontBodyLocation = "Front Right Leg";
                    } else if (x > 0.219 * width && x < 0.81 * width && y > 0.58 * height && y < 0.974 * height) {
                        frontBodyLocation = "Waist";
                    } else {
                        frontBodyLocation = null;
                    }
                    textView.setText(frontBodyLocation);

                } else{

                    if(x > 0 && x < 0.34 * width && y > 0.35 * height && y < 0.58 * height){
                        backBodyLocation = "Back Left Arm";
                    } else if(x > 0.67 * width && x < width && y > 0.35 * height && y < 0.58 * height) {
                        backBodyLocation = "Back Right Arm";
                    } else if(x > 0.277 * width && x < 0.75 * width && y > 0 && y < 0.356 * height){
                        backBodyLocation = "Head";
                    } else if(x > 0.34 * width && x < 0.67 * width && y > 0.356 * height && y < 0.58 * height){
                        backBodyLocation = "Back";
                    } else if(x > 0.04 * width && x < 0.5 * width && y > 0.786 * height && y < 0.974 * height){
                        backBodyLocation = "Back Left Leg";
                    } else if(x > 0.5 * width && x < 0.973 * width && y > 0.786 * height && y < 0.974 * height){
                        backBodyLocation = "Back Right Leg";
                    } else if (x > 0.219 * width && x < 0.81 * width && y > 0.58 * height && y < 0.974 * height){
                        backBodyLocation = " Butt";
                    } else{
                        backBodyLocation = null;
                    }
                    textView.setText(backBodyLocation);
                }
                return false;
            }
        });
    }

    public void saveBodyLocations(View view){

        Intent intent = new Intent();
        intent.putExtra("front", frontBodyLocation);
        intent.putExtra("back", backBodyLocation);
        setResult(2, intent);
        finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        // if clicked the save button,
        if (id == android.R.id.home) {
            Intent intent = new Intent();
            setResult(100,intent);
            Log.i("CWei", "finished");
            finish();
//            Bundle bundle = this.getIntent().getExtras();
//            Intent intent = new Intent(this, ViewProblemList.class);
//            intent.putExtras(bundle);
//            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }




}
