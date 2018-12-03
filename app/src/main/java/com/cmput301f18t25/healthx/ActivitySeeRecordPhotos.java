/*
 * Class Name: ActivitySeeRecordPhotos
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;


import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * This is the activity that presents all the photos for a record to the user.
 *
 *
 * @author Ajay
 * @version 1.0
 *
 */
public class ActivitySeeRecordPhotos extends AppCompatActivity {
    ViewPager viewPager;
    protected ArrayList<Drawable> images = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Record record = (Record) getIntent().getSerializableExtra("Record");
        ArrayList<String> imageList= record.getImageURIs();
        for(int x= 0; x<imageList.size(); x++){
            String image_path = imageList.get(x);
            Drawable drawable = Drawable.createFromPath(image_path);
            images.add(drawable);
         }
        viewPager = (ViewPager) findViewById(R.id.slideShow);
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter(this,images);
        viewPager.setAdapter(slideShowAdapter);
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