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


    private String userCode;
    Button mAddButton;
    EditText mUserCode;
    String doctorID;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        // if clicked the save button,
        if (id == android.R.id.home) {
            Intent intent = new Intent();
            setResult(10,intent);
            //Log.i("CWei", "finished");
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

        mUserCode = (EditText) findViewById(R.id.code_input); // R.id.userid specifies textview

        String userCode = mUserCode.getText().toString();
        //Log.i("CWei", userId);
        ElasticSearchUserController.GetRequestCodeTask requestCodeTask = new ElasticSearchUserController.GetRequestCodeTask();

        try {

            ArrayList<RequestCode> requestCodes = requestCodeTask.execute(userCode).get();
            // the problem is here -- requestCodes array has size 0 -- Ivan help me
            RequestCode requestCode = requestCodes.get(0);

            if (!(requestCode == null)){
                ElasticSearchUserController.CheckPatientTaskRequestCode checkPatientTask = new ElasticSearchUserController.CheckPatientTaskRequestCode();

                try {

                    ArrayList<RequestCode> requestCodes2 = checkPatientTask.execute(userCode).get();
                    RequestCode requestCode2 = requestCodes2.get(0);

                    if (!(requestCode2 == null)){
                        requestCode.setDoctorID(doctorID);


                        ElasticSearchUserController.AddPatientRequestCodeTask patientRequestCodeTask = new ElasticSearchUserController.AddPatientRequestCodeTask();
                        patientRequestCodeTask.execute(requestCode);

                        Toast toast = Toast.makeText(getApplicationContext(), "You have added "+requestCode.getUsername() , Toast.LENGTH_SHORT);
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
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid code!!", Toast.LENGTH_SHORT);
                toast.show();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
