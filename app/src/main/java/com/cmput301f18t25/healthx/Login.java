package com.cmput301f18t25.healthx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }
}
