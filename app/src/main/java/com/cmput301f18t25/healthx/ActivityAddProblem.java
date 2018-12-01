package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
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

public class ActivityAddProblem extends AppCompatActivity {

    private ProblemList mProblemList = ProblemList.getInstance();
    private OfflineBehaviour offline = OfflineBehaviour.getInstance();
    public String problemFrontBodyLocation;
    public String problemBackBodyLocation;
    TextView frontTextview;
    TextView backTextview;
    Uri imageFileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        frontTextview = findViewById(R.id.front_textview);
        backTextview = findViewById(R.id.back_textview);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        // if clicked the save button,
        if (id == android.R.id.home) {
            Intent intent = new Intent();
            setResult(10,intent);
            Log.i("CWei", "finished");
            finish();
//            Bundle bundle = this.getIntent().getExtras();
//            Intent intent = new Intent(this, ViewProblemList.class);
//            intent.putExtras(bundle);
//            startActivity(intent);
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

//            // Check if app is connected to a network.
//            Problem newProblem = new Problem(problemTitle, problemDescription, problemDate, mProblemList.getUser().getId());
//            mProblemList.addToProblemList(newProblem);
//            OfflineBehaviour offline = new OfflineBehaviour();

            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null == activeNetwork) {
                Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
//                newProblem.setId(UUID.randomUUID().toString()); // set a random id
//                offline.addItem(newProblem, "ADD");
//                finish();
            } else {
                Problem newProblem = new Problem(problemTitle, problemDescription, problemDate, mProblemList.getUser().getId(), problemFrontBodyLocation, problemBackBodyLocation);
                //Bundle bundle = getIntent().getExtras();
                Toast.makeText(this,problemDate,Toast.LENGTH_LONG).show();
                ElasticSearchProblemController.AddProblemTask addProblemTask = new ElasticSearchProblemController.AddProblemTask();
                addProblemTask.execute(newProblem);
                try {
                    Thread.sleep(1000);                 //1000 milliseconds is one second.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                Intent intent = new Intent();
                setResult(10,intent);
                Log.i("CWei", "finished adding");
                finish();
//

            }



        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2) {
            problemFrontBodyLocation = data.getStringExtra("front");
            problemBackBodyLocation = data.getStringExtra("back");
            frontTextview.setText(problemFrontBodyLocation);
            backTextview.setText(problemBackBodyLocation);

        }
    }


    public void addBodyLocation(View view) {
        Intent intent = new Intent(ActivityAddProblem.this, ActivityBodyLocation.class);
        startActivityForResult(intent, 2);

    }

    public void addBodyLocationPhoto(View view) {
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
        Log.d("UWU", imageFilePath);
        File imageFile = new File(folder,imageFilePath);
        imageFileUri = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }
}
