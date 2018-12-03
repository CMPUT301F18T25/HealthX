package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.cmput301f18t25.healthx.PermissionRequest.verifyPermission;

public class ActivityEditProblem extends AppCompatActivity {
    String title;
    String description;
    String dateString;
    String userId;
    String frontBodyPhoto;
    String backBodyPhoto;
    String frontBodyLocation;
    String backBodyLocation;
    Problem oldProblem;
    Problem newProblem;
    int problemPosition;
    private ProblemList mProblemList = ProblemList.getInstance();
    private OfflineBehaviour offline = OfflineBehaviour.getInstance();
    Uri imageFileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_FRONT = 100;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BACK = 101;
    TextView frontTextview;
    TextView backTextview;
    ImageView frontView;
    ImageView backView;
    Button edit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        EditText title_textView = findViewById(R.id.title_input);
        DatePicker dateStarted_textView = findViewById(R.id.dateStarted_input);
        EditText description_textView = findViewById(R.id.description_input);

        frontTextview = findViewById(R.id.front_textview);
        backTextview = findViewById(R.id.back_textview);
        frontView = findViewById(R.id.view_front);
        backView = findViewById(R.id.view_back);
        edit = findViewById(R.id.editPhoto);

        oldProblem = (Problem) bundle.getSerializable("problem");
        title = oldProblem.getTitle();
        description = oldProblem.getDescription();
        dateString = oldProblem.getDate();
        userId = oldProblem.getUserId();
        frontBodyPhoto = oldProblem.getFrontPhoto();
        if (frontBodyPhoto != null){
            edit.setVisibility(View.VISIBLE);
        }
//        Log.d("Sandy 301", frontBodyPhoto);
        backBodyPhoto = oldProblem.getBackPhoto();
        frontBodyLocation = oldProblem.frontBodyLocation;
        backBodyLocation = oldProblem.backBodyLocation;

        problemPosition = bundle.getInt("position");
        title_textView.setText(title);
        description_textView.setText(description);
        dateStarted_textView.updateDate(Integer.valueOf(dateString.substring(0, 4)),
                Integer.valueOf(dateString.substring(5, 7)) - 1,
                Integer.valueOf(dateString.substring(8, 10)));
        frontTextview.setText(frontBodyLocation);
        backTextview.setText(backBodyLocation);
        frontView.setImageDrawable(Drawable.createFromPath(frontBodyPhoto));
//        frontView.setImageBitmap(BitmapFactory.decodeFile(frontBodyPhoto));
        backView.setImageDrawable(Drawable.createFromPath(backBodyPhoto));

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
//<<<<<<< HEAD
            if (id == android.R.id.home) {
                Intent intent = new Intent();
                setResult(10,intent);
                finish();
            }
//=======
//            Bundle bundle = this.getIntent().getExtras();
//            Intent intent = new Intent(this, ViewProblemList.class);
//            intent.putExtras(bundle);
//            startActivity(intent);
//>>>>>>> master
        }
        if (id == R.id.save_button) {

            EditText title_textView = findViewById(R.id.title_input);
            DatePicker dateStarted_textView = findViewById(R.id.dateStarted_input);
            EditText description_textView = findViewById(R.id.description_input);


            String problemTitle = title_textView.getText().toString();
            Date selected = new Date(dateStarted_textView.getYear() - 1900,
                    dateStarted_textView.getMonth(), dateStarted_textView.getDayOfMonth());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String problemDate = format.format(selected);
            String problemDescription = description_textView.getText().toString();
//            Problem newProblem = new Problem(problemTitle, problemDescription, problemDate, userId);

            // Check if app is connected to a network.
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null == activeNetwork) {
//                mProblemList.removeProblemFromList(problemPositon);
////                mProblemList.addToProblemList(newProblem);
////                offline.addItem(oldProblem, "DELETE");
////                offline.addItem(newProblem, "ADD");
                Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
//                finish();
            } else {

                Bundle bundle = getIntent().getExtras();


                String pID = oldProblem.getId();

                //Problem newProblem = new Problem(problemTitle, problemDescription, problemDate, userId, "","", "","");
                ElasticSearchProblemController.DeleteProblemTask deleteProblemTask = new ElasticSearchProblemController.DeleteProblemTask();
                deleteProblemTask.execute(oldProblem);
                String frontBodyLocation = oldProblem.getFrontBodyLocation();
                String backBodyLocation = oldProblem.getBackBodyLocation();
                mProblemList.removeProblemFromList(problemPosition);

                newProblem = new Problem(problemTitle, problemDescription, problemDate, userId,frontBodyPhoto,backBodyPhoto,frontBodyLocation,backBodyLocation);
                newProblem.setId(pID);

                ElasticSearchProblemController.UpdateProblemTask updateProblemTask = new ElasticSearchProblemController.UpdateProblemTask();
                updateProblemTask.execute(newProblem);
                mProblemList.addToProblemList(newProblem);

                Log.d("CWei",oldProblem.getId()+ " "+oldProblem.getTitle());
                Log.d("CWei",newProblem.getId()+ " "+newProblem.getTitle());

                try {
                    Thread.sleep(1000);                 //1000 milliseconds is one second.
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


    public void addBodyLocation(View view) {
        Intent intent = new Intent(ActivityEditProblem.this, ActivityBodyLocation.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            frontBodyLocation = data.getStringExtra("front");
            backBodyLocation = data.getStringExtra("back");
            frontTextview.setText(frontBodyLocation);
            backTextview.setText(backBodyLocation);

        } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_FRONT) {
            frontBodyPhoto = imageFileUri.getPath();
            frontView.setImageDrawable(Drawable.createFromPath(frontBodyPhoto));
        } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BACK) {
            backBodyPhoto = imageFileUri.getPath();
            backView.setImageDrawable(Drawable.createFromPath(backBodyPhoto));
        } else if (requestCode == 3){

            byte[] byteArray = data.getByteArrayExtra("result");
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 3500, 3000, false);
            Drawable drawable = new BitmapDrawable(bitmapScaled);
            frontView.setImageDrawable(drawable);

//            String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
//            File folderF = new File(folder);
//            if (!folderF.exists()) {
//                folderF.mkdir();/
//            }
//
//            try {
//                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
//                m.invoke(null);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            verifyPermission(this);
//
//            String imageFilePath = String.valueOf(System.currentTimeMillis()) + ".jpg";
//            File imageFile = new File(folder,imageFilePath);
//            imageFileUri = Uri.fromFile(imageFile);

        }
        
    }
    public void Editphoto(View view) {
//        Bitmap bitmap = BitmapFactory.decodeFile(frontBodyPhoto);
        Intent intent = new Intent(getApplicationContext(),DrawBitmap.class);
//        intent.putExtra("bitmap",bitmap);
        intent.putExtra("path",frontBodyPhoto);
        startActivityForResult(intent,3);

    }
}
