package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import io.searchbox.core.Get;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
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
        } else {
//            User user = new User(name,id,phone,email,status);
            ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
            getUserTask.execute("ilim");
//                        createUser(UserName);
//                        saveUsernameInFile(UserName); // save username for auto login
//            Intent intent = new Intent(Signup.this, Login.class);
//            startActivity(intent);
            Intent intent = new Intent(this, ViewProblemList.class);
            startActivity(intent);
        }
    }

}


