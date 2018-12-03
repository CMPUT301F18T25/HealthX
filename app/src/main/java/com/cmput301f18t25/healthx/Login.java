/*
 * Class Name: Login
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Get;

public class Login extends AppCompatActivity {

    EditText userIdTextView;
    private User user;
    private ProblemList mProblemList = ProblemList.getInstance();

    private OfflineBehaviour offline = OfflineBehaviour.getInstance();
    private  OfflineSave offSave;
    private UserList mUserList = UserList.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        userIdTextView = findViewById(R.id.loginUserID);
        offSave = new OfflineSave(getApplicationContext());

        if (mUserList.getPreviousUser() != null) {
            userIdTextView.setText(mUserList.getPreviousUser().getUsername());
        } else {
            User user = offSave.loadUserFromFile();
            if (user != null) {
                userIdTextView.setText(user.getUsername());
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mUserList.getPreviousUser() != null) {
            userIdTextView.setText(mUserList.getPreviousUser().getUsername());
        }


    }

    public void toCodeLogin(View view) {
        Intent intent = new Intent(this, CodeLogin.class);
        startActivity(intent);
    }

    public void toSignUp(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    public void toViewProblem(View view) {
        String userId = userIdTextView.getText().toString();
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {

            User u = mUserList.getUserByUsername(userId);
            if (u != null) {
                mProblemList.setUser(u);
                mUserList.setPreviousUser(u);
               CheckUser(u);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid Credentials & Offline!" , Toast.LENGTH_SHORT);
                toast.show();
            }


        } else {
            ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
            try {
                user = getUserTask.execute(userId).get();
                offSave.saveUserToFile(user);
                Toast.makeText(getApplicationContext(), user.getName() , Toast.LENGTH_LONG).show();
                mUserList.setPreviousUser(user);
                CheckUser(user);
                mProblemList.setUser(user);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public void CheckUser(User user) {
        if (user.getStatus().equals("Patient")){
            mProblemList.setUser(user);
            Bundle bundle = new Bundle();
            bundle.putString("id",user.getUsername());


            Intent intent = new Intent(this, ViewProblemList.class);
            intent.putExtras(bundle);
            startActivity(intent);

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

        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid Credentials!" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


