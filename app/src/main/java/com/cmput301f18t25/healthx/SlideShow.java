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
import android.widget.Toast;

import java.util.ArrayList;

public class SlideShow extends AppCompatActivity {
    ViewPager viewPager;
    protected ArrayList<Drawable> images = new ArrayList<>();
    //ProblemList problemList = ProblemList.getInstance();
    ArrayList<Record> recordList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        recordList = (ArrayList<Record>) getIntent().getSerializableExtra("Records");
        //for(int i = 0; i<problemList.getListCount(); i++){
//            Problem problem = problemList.getElementByIndex(i);
//            recordList = problem.getRecordArray();
////            Toast.makeText(this,"Reached ",Toast.LENGTH_LONG).show();
//            Toast.makeText(this,String.valueOf(i),Toast.LENGTH_LONG).show();
        Log.d("Num Records:",String.valueOf(recordList.size()));
        for(int pos= 0; pos<recordList.size(); pos++){
            Record record = recordList.get(pos);
            Log.d("Sandy 301","Reached");
            ArrayList<String> imageList= record.getImageURIs();
            //String image_path = imageList.get(pos);
            //Drawable drawable = Drawable.createFromPath(image_path);
            for(int x= 0; x<imageList.size(); x++){
                Log.d("Sandy 301","Reached");
                String image_path = imageList.get(x);
                Drawable drawable = Drawable.createFromPath(image_path);
//                Bitmap bitmap = record.getImage();
//                Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
//                Drawable drawable = new BitmapDrawable(bitmapScaled);
//                images.add(drawable);
                images.add(drawable);
        }

        }

        viewPager = (ViewPager) findViewById(R.id.slideShow);
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter(this,images);

        viewPager.setAdapter(slideShowAdapter);
    }
}
