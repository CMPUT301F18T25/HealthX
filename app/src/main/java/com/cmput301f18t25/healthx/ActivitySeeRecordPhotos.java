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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class ActivitySeeRecordPhotos extends AppCompatActivity {
    ViewPager viewPager;
    protected ArrayList<Drawable> images = new ArrayList<>();
    //ProblemList problemList = ProblemList.getInstance();
    //ArrayList<Record> recordList;
    //Record rRecord  = (Record) this.getIntent().getSerializableExtra("Record");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        Record record = (Record) getIntent().getSerializableExtra("Record");
        ArrayList<String> imageList= record.getImageURIs();
        //Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
        //Drawable drawable = new BitmapDrawable(bitmapScaled);
        //images.add(drawable);
        for(int x= 0; x<imageList.size(); x++){
            Log.d("Sandy 301","Reached");
            String image_path = imageList.get(x);
            Drawable drawable = Drawable.createFromPath(image_path);
            //Bitmap bitmap = record.getImage();
            //Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
            //Drawable drawable = new (bitmapScaled);
            images.add(drawable);
         }
        viewPager = (ViewPager) findViewById(R.id.slideShow);
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter(this,images);
        viewPager.setAdapter(slideShowAdapter);
    }
}