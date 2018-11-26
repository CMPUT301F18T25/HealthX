package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAddButton = (Button) findViewById(R.id.btnAddPatient); // R.id.idofButton once created
        mUserText = (EditText) findViewById(R.id.userIdText); // R.id.userid specifies textview
        mEmailText = (EditText) findViewById(R.id.userEmailText);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        // if clicked the save button,
        if (id == android.R.id.home) {
            Bundle bundle = this.getIntent().getExtras();
            Intent intent = new Intent(this, ViewPatientList.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void addPatient(View view) {

        String userId = mUserText.getText().toString();
        String userEmail = mEmailText.getText().toString();
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        try {

            User user = getUserTask.execute(userId,userEmail).get();
            Toast.makeText(getApplicationContext(), user.getName() , Toast.LENGTH_LONG).show();
            if (user.getStatus().equals("Patient")) {
                //////////////////// add patient




                /////////////////////////////////////
                Toast toast = Toast.makeText(getApplicationContext(), "You have added "+user.getName() , Toast.LENGTH_SHORT);
                toast.show();
//                Intent intent = new Intent(this, ViewPatientList.class);
//                Bundle bundle = getIntent().getExtras();
//                intent.putExtras(bundle);
//                startActivity(intent);
                finish();

            }

            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid Credientials!" , Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
