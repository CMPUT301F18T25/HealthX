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
        Bundle bundle = this.getIntent().getExtras();
        Record record = (Record) bundle.getParcelable("Record");
        Bitmap bitmap = record.getImage();
        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
        Drawable drawable = new BitmapDrawable(bitmapScaled);
        images.add(drawable);
        /*for(int x= 0; x<recordList.size(); x++){
            Log.d("Sandy 301","Reached");
            Record record = recordList.get(x);
            Bitmap bitmap = record.getImage();
            Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
            Drawable drawable = new BitmapDrawable(bitmapScaled);
            images.add(drawable);

        }*/
        /*LinearLayout gallery = findViewById(R.id.gallery);

        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i<6;i++){

            View view = inflater.inflate(R.layout.picture,gallery,false);

            ImageView imageView = view.findViewById(R.id.imageView2);
            imageView.setImageResource(R.mipmap.ic_launcher);

            gallery.addView(view);
        }*/
        viewPager = (ViewPager) findViewById(R.id.slideShow);
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter(this,images);

        viewPager.setAdapter(slideShowAdapter);
    }

}
