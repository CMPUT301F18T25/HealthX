/*
 * Class Name: SlideShow
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * This is the activity that allows the user to navigate their saved photos for records/problems.
 *
 * @author Ivan
 * @author Sandy
 * @author Ajay
 * @version 1.0
 *
 */
public class SlideShow extends AppCompatActivity {
    ViewPager viewPager;
    protected ArrayList<Drawable> images = new ArrayList<>();
    ArrayList<Record> recordList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        recordList = (ArrayList<Record>) getIntent().getSerializableExtra("Records");

        for(int pos= 0; pos<recordList.size(); pos++){
            Record record = recordList.get(pos);
            ArrayList<String> imageList= record.getImageURIs();
            for(int x= 0; x<imageList.size(); x++){

                String image_path = imageList.get(x);
                Drawable drawable = Drawable.createFromPath(image_path);
                images.add(drawable);
        }

        }

        viewPager = (ViewPager) findViewById(R.id.slideShow);
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter(this,images);

        viewPager.setAdapter(slideShowAdapter);
    }
}
