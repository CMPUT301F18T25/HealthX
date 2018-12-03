/*
 * Class Name: Signup
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * This is the activity that allows the user to sign up.
 *
 * @author Cecilia
 * @author Dhruba
 * @author Aida
 * @version 1.0
 *
 */

public class Signup extends AppCompatActivity {

    private OfflineSave offlineSave;
    OfflineBehaviour offlineBehaviour = OfflineBehaviour.getInstance();
    ProblemList mProblemList = ProblemList.getInstance();
    UserList mUserList = UserList.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        offlineSave = new OfflineSave(this);

        final EditText id_textView = findViewById(R.id.input_id);
        final EditText name_textView = findViewById(R.id.input_name);
        final EditText email_textView = findViewById(R.id.input_email);
        final EditText phone_textView = findViewById(R.id.input_phone);

        final RadioGroup statusGroup = findViewById(R.id.status_group);
        Button SignUpButton = (Button) findViewById(R.id.btn_signup);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedId = statusGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(checkedId);
                String status = radioButton.getText().toString();

                String name = name_textView.getText().toString();
                String id = id_textView.getText().toString();
                String email = email_textView.getText().toString();
                String phone = phone_textView.getText().toString();
                /**  Check if app is connected to a network.
                 * first check if the user is in the table
                 * in no then  add, else prompt the user to enter something else
                 * else create a new user and save it into the file system
                 **/
                User user = new User(name,id,phone,email,status,"None");
                user.setId(UUID.randomUUID().toString());
                /**  save user into file
                 */
                offlineSave.saveUserToFile(user);
                /**  check if we have connectivity
                 */
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (null == activeNetwork) {
                    /**  no connectivity
                     */
                    offlineBehaviour.addItem(user, "SignUp"); // query it for elastic search to add it to elastic search
                    Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
                    // save user into the file ..
                } else {
                    ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
                    try {
                        User check = getUserTask.execute(id).get();
                        if (!check.getStatus().equals("")){
                            Toast.makeText(getApplicationContext(), "The id is already taken.", Toast.LENGTH_SHORT).show();

                        }
                        else if (id.length() < 8){
                            Toast.makeText(getApplicationContext(), "Your id should be more than 8 characters.", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
                            addUserTask.execute(user);
                            toViewProblem(user);
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }

        });
    }


    /**
     * after sign up, we directs the user to his problem list
     * */

    public void toViewProblem(User user) {
        mUserList.setPreviousUser(user);
        if (user.getStatus().equals("Patient")){
            mProblemList.setUser(user);
            Bundle bundle = new Bundle();
            bundle.putString("id",user.getUsername());

            Intent mintent = new Intent(this, ViewProblemList.class);
            mintent.putExtras(bundle);
            startActivity(mintent);
        }
        else if (user.getStatus().equals("Care Provider")){

            Bundle bundle = new Bundle();
            bundle.putString("id",user.getUsername());
            Intent mintent = new Intent(this, ViewPatientList.class);
            mintent.putExtras(bundle);
            startActivity(mintent);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean checkIfValid(String username) {
        if (username.length() < 8) {
            Toast toast = Toast.makeText(this,"Userid Too Short",  Toast.LENGTH_SHORT);
            toast.show();
            return false;

        }
        return true;
        // now implement if hits == 0 for elastic search;
    }

}