/*
 * Class Name: ActivityAddProblem
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.cmput301f18t25.healthx.PermissionRequest.verifyPermission;

/**
 * This is the activity that allows the user to add a problem, with body photo and body location.
 *
 * @author Dhruba
 * @author Ivan
 * @author Sandy
 * @author Cecilia
 * @version 1.0
 *
 */

public class ActivityAddProblem extends AppCompatActivity {

    private ProblemList mProblemList = ProblemList.getInstance();
    private OfflineBehaviour offline = OfflineBehaviour.getInstance();
    public String problemFrontBodyLocation;
    OfflineSave offlineSave;
    public String problemBackBodyLocation;
    public String problemFrontPhoto;
    public String problemBackPhoto;

    TextView frontTextview;
    TextView backTextview;
    ImageView frontView;
    ImageView backView;


    Uri imageFileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_FRONT = 100;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BACK = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offlineSave = new OfflineSave(this);
        setContentView(R.layout.activity_add_problem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        frontTextview = findViewById(R.id.front_textview);
        backTextview = findViewById(R.id.back_textview);
        frontView = findViewById(R.id.view_front);
        backView = findViewById(R.id.view_back);
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     *
     * @param menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item text view to switch to add patient by code
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent();
            setResult(10,intent);
            finish();
        }
        if (id == R.id.save_button) {


            EditText title_textView = (EditText) findViewById(R.id.title_input);
            DatePicker dateStarted_textView = findViewById(R.id.dateStarted_input);
            EditText description_textView = (EditText) findViewById(R.id.description_input);


            String problemTitle = title_textView.getText().toString();
            Date selected = new Date(dateStarted_textView.getYear() - 1900,
                    dateStarted_textView.getMonth(), dateStarted_textView.getDayOfMonth());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String problemDate = format.format(selected);
            String problemDescription = description_textView.getText().toString();


            Problem newProblem = new Problem(problemTitle, problemDescription, problemDate, mProblemList.getUser().getId(), problemFrontPhoto, problemBackPhoto, problemFrontBodyLocation, problemBackBodyLocation);
            newProblem.setId(UUID.randomUUID().toString()); // might need to get rid of this haha
            mProblemList.addToProblemList(newProblem);
            offlineSave.saveProblemListToFile(newProblem);
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null == activeNetwork) {
                Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();

                offline.addItem(newProblem, "ADD");
                finish();
            } else {
                Toast.makeText(this,problemDate,Toast.LENGTH_LONG).show();
                ElasticSearchProblemController.AddProblemTask addProblemTask = new ElasticSearchProblemController.AddProblemTask();
                addProblemTask.execute(newProblem);
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                Intent intent = new Intent();
                setResult(10,intent);
                finish();

            }



        }
        return super.onOptionsItemSelected(item);

    }

    /**
     * Comes back to the current activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2) {
            problemFrontBodyLocation = data.getStringExtra("front");
            problemBackBodyLocation = data.getStringExtra("back");
            frontTextview.setText(problemFrontBodyLocation);
            backTextview.setText(problemBackBodyLocation);

        } else if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_FRONT){
            problemFrontPhoto = imageFileUri.getPath();
            frontView.setImageDrawable(Drawable.createFromPath(problemFrontPhoto));
        } else if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BACK){
            problemBackPhoto = imageFileUri.getPath();
            backView.setImageDrawable(Drawable.createFromPath(problemBackPhoto));
        }
    }

    /**
     * handles on click for the add body location button
     *
     * @param view
     */

    public void addBodyLocation(View view) {
        Intent intent = new Intent(ActivityAddProblem.this, ActivityBodyLocation.class);
        startActivityForResult(intent, 2);

    }

    /**
     * handles on click for the add body location front button
     *
     * @param view
     */

    public void addBodyLocationPhotoFront(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }

        try {
            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
            m.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        verifyPermission(this);

        String imageFilePath = String.valueOf(System.currentTimeMillis()) + ".jpg";
        File imageFile = new File(folder,imageFilePath);
        imageFileUri = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_FRONT);

    }

    /**
     * handles on click for the add body location photo back button
     *
     * @param view
     */
    public void addBodyLocationPhotoBack(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }

        try {
            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
            m.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        verifyPermission(this);

        String imageFilePath = String.valueOf(System.currentTimeMillis()) + ".jpg";
        File imageFile = new File(folder,imageFilePath);
        imageFileUri = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BACK);

    }


}
