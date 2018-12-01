package com.cmput301f18t25.healthx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class ActivityAddPatient extends AppCompatActivity {


    // will do oncreatebundle soon



    // once we have built the xml file, we first specify two private variables
    // User -id which is a string and this is read from the user input via the edit text
    //


    private String userID;
    Button mAddButton;
    EditText mUserText;
    EditText mEmailText;
    String doctorID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAddButton = (Button) findViewById(R.id.btnAddPatient); // R.id.idofButton once created

        Bundle bundle = this.getIntent().getExtras();
        doctorID = bundle.getString("doctorID");

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
        }
        return super.onOptionsItemSelected(item);
    }
    public void addPatient(View view) {
        mUserText = (EditText) findViewById(R.id.userIdText); // R.id.userid specifies textview

        String userId = mUserText.getText().toString();
        Log.i("CWei", userId);
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        try {

            User user = getUserTask.execute(userId).get();
            if (!user.getStatus().equals("")){
                ElasticSearchUserController.CheckPatientTask checkPatientTask = new ElasticSearchUserController.CheckPatientTask();
                try {

                    User user2 = checkPatientTask.execute(userId).get();
                    if (user2.getStatus().equals("")){
                        user.setDoctorID(doctorID);
                        ElasticSearchUserController.AddPatientTask addPatientTask = new ElasticSearchUserController.AddPatientTask();
                        addPatientTask.execute(user);

                        Toast toast = Toast.makeText(getApplicationContext(), "You have added "+user.getName() , Toast.LENGTH_SHORT);
                        toast.show();
                        try {
                            Thread.sleep(1000);                 //1000 milliseconds is one second.
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        //Intent intent = new Intent(this,ViewPatientList.class);
                        Intent intent = new Intent();
                        setResult(10,intent);
                        Log.i("CWei", "finished adding");
                        finish();
                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "This patient is already signed!" , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid credentials!!", Toast.LENGTH_SHORT);
                toast.show();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
