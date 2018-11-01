package com.cmput301f18t25.healthx;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPatient extends AppCompatActivity {


    // will do oncreatebundle soon



    // once we have built the xml file, we first specify two private variables
    // User -id which is a string and this is read from the user input via the edit text
    //


    private String userID;
    Button mAddButton;
    EditText mUserText;

    @Override
    public void onCreate(@androidx.annotation.Nullable @Nullable Bundle savedInstanceState, @androidx.annotation.Nullable @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_add_patient);
        mAddButton = (Button) findViewById(R.id.btnAddPatient); // R.id.idofButton once created
        mUserText = (EditText) findViewById(R.id.userIdText); // R.id.userid specifies textview

    }

    public void searchUser(View view) {
        userID = mUserText.getText().toString();
    }

}
