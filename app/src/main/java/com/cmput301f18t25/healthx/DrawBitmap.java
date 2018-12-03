/*
 * Class Name: DrawBitmap
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;

import static com.cmput301f18t25.healthx.PermissionRequest.verifyPermission;

/**
 * This is the class to draw bitmap
 *
 *
 * @author Sandy
 * @version 1.0
 *
 */

public class DrawBitmap extends AppCompatActivity {

    FreeDraw dv;
    ImageView imageView;
    Button clear;
    Button save;
    Bitmap userBitmap;
    ImageView testArea;
    String path;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_bitmap);
//        clear = findViewById(R.id.clear);
        save = findViewById(R.id.save);
        imageView = findViewById(R.id.testview);


        Intent intent = getIntent();
        path =intent.getStringExtra("path");
        Log.d("Sandy 301",path);
//        Uri fileuri = Uri.parse(path);

//        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
//        imageView.setImageURI(fileuri);

        bitmap = BitmapFactory.decodeFile(path);
        Bitmap drawableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);

//        dv = new FreeDraw(this);

//        dv.setmBitmap(bitmap);

//        imageView.setImageBitmap(bitmap);

//        dv = new FreeDraw(this,drawableBitmap);

        dv = (FreeDraw) findViewById(R.id.draw);
        dv.setCanvasBitmap(drawableBitmap);







//        clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("SANDY 301","idgaf");
//                dv.clear();
//            }
//        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBitmap = dv.saveDrawing();
                Log.d("SANDY 301",userBitmap.toString());
                Log.d("SANDY 301","Bitmap Saved");





//                ImageView imageView = findViewById(R.id.testview);
                imageView.setImageBitmap( userBitmap );
                //String temp = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Intent returnIntent = new Intent();
                //Log.d("UWU", temp);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                userBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                returnIntent.putExtra("result", byteArray);
                setResult(1, returnIntent);
                finish();

            }
        });

    }

    

}
