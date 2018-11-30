package com.cmput301f18t25.healthx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ActivitySeeRecordPhotos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_record_photos);

        LinearLayout gallery = findViewById(R.id.gallery);

        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i<6;i++){

            View view = inflater.inflate(R.layout.picture,gallery,false);

            ImageView imageView = view.findViewById(R.id.imageView2);
            imageView.setImageResource(R.mipmap.ic_launcher);

            gallery.addView(view);
        }
    }

}
