/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SlideShow extends AppCompatActivity {
    ViewPager viewPager;
    protected ArrayList<Drawable> images = new ArrayList<>();
    ProblemList problemList = ProblemList.getInstance();
    ArrayList<Record> recordList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        for(int i = 0; i<problemList.getListCount(); i++){
            Problem problem = problemList.getElementByIndex(i);
            recordList = problem.getRecordArray();
            for(int x = 0 ; x<recordList.size(); x++){
                Record record = recordList.get(x);
                Bitmap bitmap = record.getImage();
                Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
                Drawable drawable = new BitmapDrawable(bitmapScaled);
                Log.d("Sandy 301",drawable.toString());
                images.add(drawable);
            }


        }

        viewPager = (ViewPager) findViewById(R.id.slideShow);
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter(this,images);

        viewPager.setAdapter(slideShowAdapter);
    }
}
