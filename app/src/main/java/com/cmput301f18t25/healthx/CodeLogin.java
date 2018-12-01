package com.cmput301f18t25.healthx;

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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Get;

import java.util.concurrent.ExecutionException;

public class CodeLogin extends AppCompatActivity {

    EditText userCodeTextView;
    private RequestCode requestCode;
    private ProblemList mProblemList = ProblemList.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_code);
        getSupportActionBar().hide();
        userCodeTextView = findViewById(R.id.loginUserCode);
    }

    public void toMainLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void toSignUp(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    public void toViewProblemCode(View view) {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {
            Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
        } else {
//          User user = new User(name,id,phone,email,status);
            String userCode = userCodeTextView.getText().toString();
            ElasticSearchUserController.GetRequestCodeTask getRequestCodeTask = new ElasticSearchUserController.GetRequestCodeTask();
            try {

                ArrayList<RequestCode> requestCodes = getRequestCodeTask.execute(userCode).get();
                requestCode = requestCodes.get(0);

                String username = requestCode.getUsername();
                if (!username.equals("")) {


                    Log.d("here", username);
                    ElasticSearchUserController.GetUserTask userTask = new ElasticSearchUserController.GetUserTask();
                    User user = userTask.execute(username).get();
                    Log.d("heree", user.getName());
                    Toast.makeText(getApplicationContext(), user.getName(), Toast.LENGTH_LONG).show();
                    if (!user.getStatus().equals("")) {
                        mProblemList.setUser(user);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", user.getUsername());
                        bundle.putString("email", user.getEmail());
                        bundle.putString("code", user.getCode());


                        ElasticSearchUserController.DeleteRequestCodeTask deleteRequestCodeTask = new ElasticSearchUserController.DeleteRequestCodeTask();
                        deleteRequestCodeTask.execute(requestCode);

                        Intent intent = new Intent(this, ViewProblemList.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Invalid Code!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid Code!" , Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//                        createUser(UserName);
//                        saveUsernameInFile(UserName); // save username for auto login
//            Intent intent = new Intent(Signup.this, Login.class);
//            startActivity(intent);

        }
        }

    }




