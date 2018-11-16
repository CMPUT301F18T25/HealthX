package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ActivityAddPhoto extends AppCompatActivity {

    ImageView imageView;
    Button btnSave;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        btnSave = findViewById(R.id.savePicture);
        imageView = findViewById(R.id.imageView);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }else{
            Toast.makeText(ActivityAddPhoto.this,"Unable To Get Photo From Camera",Toast.LENGTH_LONG).show();
        }

    }

    public void savePicture(View view){

        Intent returnPhotoIntent = new Intent();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        returnPhotoIntent.putExtra("image", byteArray);
        setResult(1, returnPhotoIntent);
        finish();

    }

}
