/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DrawBitmap extends AppCompatActivity {

    FreeDraw dv;
    ImageView imageView;
    Button clear;
    Button save;
    Bitmap userBitmap;
    ImageView testArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_bitmap);
        clear = findViewById(R.id.clear);
        save = findViewById(R.id.save);

        dv = (FreeDraw) findViewById(R.id.draw);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SANDY 301","idgaf");
                dv.clear();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBitmap = dv.saveDrawing();
                Log.d("SANDY 301",userBitmap.toString());
                Log.d("SANDY 301","Bitmap Saved");

                ImageView imageView = findViewById(R.id.testview);
                imageView.setImageBitmap( userBitmap );


            }
        });

    }

    

}
