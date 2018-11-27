/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class SlideShow extends AppCompatActivity {
    ViewPager viewPager;
    Record record;
    Bitmap bitmap;
    protected ArrayList<Drawable> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        viewPager = (ViewPager) findViewById(R.id.slideShow);
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter(this);

        viewPager.setAdapter(slideShowAdapter);
    }
}
