/*
 * Class Name: ActivityAddPatientByCode
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */


package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActivityAddPatientByCode extends AppCompatActivity {


    Button mAddButton;
    EditText mUserCode;
    String doctorID;
    User cPatient;
    User cUser;
    ArrayList<RequestCode> requestCodes = new ArrayList<RequestCode>();
    RequestCode requestCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_by_code);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAddButton = (Button) findViewById(R.id.btnAddPatientbyCode);

        Bundle bundle = this.getIntent().getExtras();
        assert(bundle != null);
        doctorID = bundle.getString("doctorID");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent();
            setResult(10,intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void toMainAdd(View view) {
        Intent intent = new Intent(this, ActivityAddPatient.class);
        Bundle inBundle = new Bundle();
        inBundle.putString("doctorID",doctorID);
        intent.putExtras(inBundle);
        startActivity(intent);
    }

    public void addPatientByCode(View view) {

        mUserCode = (EditText) findViewById(R.id.code_input);

        String userCode = mUserCode.getText().toString();
        ElasticSearchUserController.GetRequestCodeTask requestCodeTask = new ElasticSearchUserController.GetRequestCodeTask();
        try {
            requestCodes = requestCodeTask.execute(userCode).get();
            requestCode = requestCodes.get(0);
            String patientUsername = requestCode.getUsername();

            if (!(requestCode == null)){

                try {

                    ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
                    cPatient = getUserTask.execute(patientUsername).get();

                    ElasticSearchUserController.CheckPatientTask checkPatientTask = new ElasticSearchUserController.CheckPatientTask();
                    cUser = checkPatientTask.execute(cPatient.getUsername()).get();

                    if(cUser.getStatus().equals("")){

                        cPatient.setDoctorID(doctorID);
                        ElasticSearchUserController.AddPatientTask addPatientTask = new ElasticSearchUserController.AddPatientTask();
                        addPatientTask.execute(cPatient);
                        Toast toast = Toast.makeText(getApplicationContext(), "You have added "+cPatient.getUsername() , Toast.LENGTH_SHORT);
                        toast.show();

                        try {
                            Thread.sleep(1000);
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        Intent intent = new Intent();
                        setResult(10,intent);
                        finish();

                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Patient is already signed!" , Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }catch (InterruptedException e){

                    e.printStackTrace();

                }


            }else {
                Toast toast = Toast.makeText(getApplicationContext(), "Code invalid!!!" , Toast.LENGTH_SHORT);
                toast.show();
            }


        }catch (ExecutionException e) {

            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
