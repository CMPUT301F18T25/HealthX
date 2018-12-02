package com.cmput301f18t25.healthx;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Get;


public class Login extends AppCompatActivity {
//
//    TextInputEditText userIdTextView;
//    TextInputEditText emailtextView;

    EditText userIdTextView;
    private User user;
    private ProblemList mProblemList = ProblemList.getInstance();

    private OfflineBehaviour offline = OfflineBehaviour.getInstance();
    private  OfflineSave offSave;

    ScrollView mLayout;
    AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLayout = findViewById(R.id.animation_layout);
        animationDrawable = (AnimationDrawable) mLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        //getSupportActionBar().hide();
        userIdTextView = findViewById(R.id.loginUserID);
        offSave = new OfflineSave(getApplicationContext());
        User user  = offSave.loadUserFromFile();
        if (user != null) {
            userIdTextView.setText(user.getUsername());
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

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {
            Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
            // load from user table
        } else {
//            User user = new User(name,id,phone,email,status);
            String userId = userIdTextView.getText().toString();
            ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
            try {
                user = getUserTask.execute(userId).get();
                Toast.makeText(getApplicationContext(), user.getName() , Toast.LENGTH_LONG).show();
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
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

}


