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

public class DrawBitmap extends AppCompatActivity {

    FreeDraw dv;
    ImageView imageView;
    Button clear;
    Button save;
    Bitmap userBitmap;
    String path;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_bitmap);

        save = findViewById(R.id.save);
        imageView = findViewById(R.id.testview);


        Intent intent = getIntent();
        path =intent.getStringExtra("path");


        bitmap = BitmapFactory.decodeFile(path);
        Bitmap drawableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);


        dv = (FreeDraw) findViewById(R.id.draw);
        dv.setCanvasBitmap(drawableBitmap);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBitmap = dv.saveDrawing();

                imageView.setImageBitmap( userBitmap );

                Intent returnIntent = new Intent();
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
