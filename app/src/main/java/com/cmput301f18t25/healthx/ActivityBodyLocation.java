/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityBodyLocation extends AppCompatActivity {

    String frontBodyLocation;
    String backBodyLocation;
    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_location);
        final ImageView imgView = findViewById(R.id.body_image);
        final TextView textView = findViewById(R.id.body_image_textview);
        imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int x = (int) event.getX();
                int y = (int) event.getY();
                int width = imgView.getMeasuredWidth();
                int height = imgView.getMeasuredHeight();
                if(x > 0 && x < 0.34 * width && y > 0.35 * height && y < 0.58 * height){
                    frontBodyLocation = "Left Arm";
                } else if(x > 0.67 * width && x < width && y > 0.35 * height && y < 0.58 * height) {
                    frontBodyLocation = "Right Arm";
                } else if(x > 0.277 * width && x < 0.75 * width && y > 0 && y < 0.356 * height){
                    frontBodyLocation = "Head";
                } else if(x > 0.34 * width && x < 0.67 * width && y > 0.356 * height && y < 0.58 * height){
                    frontBodyLocation = "Chest";
                } else if(x > 0.04 * width && x < 0.5 * width && y > 0.786 * height && y < 0.974 * height){
                    frontBodyLocation = "Left Leg";
                } else if(x > 0.5 * width && x < 0.973 * width && y > 0.786 * height && y < 0.974 * height){
                    frontBodyLocation = "Right Leg";
                } else if (x > 0.219 * width && x < 0.81 * width && y > 0.58 * height && y < 0.974 * height){
                    frontBodyLocation = "Waist";
                } else{
                    frontBodyLocation = null;
                }
                textView.setText(frontBodyLocation);
                Log.d("UWU", "The coordinates for on touch are x:" + String.valueOf(x) + " y: " + String.valueOf(y));

                return false;

            }
        });
    }

}
