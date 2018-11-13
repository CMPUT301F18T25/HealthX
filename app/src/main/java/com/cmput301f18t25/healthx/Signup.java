package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmput301f18t25.healthx.Model.ElasticSearchAuthentication;

public class Signup extends AppCompatActivity {

    EditText id_textView, name_textView, email_textView, phone_textView;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button addbutton = (Button) findViewById(R.id.btn_signup);
        id_textView = findViewById(R.id.input_id);
        name_textView = findViewById(R.id.input_name);
        email_textView = findViewById(R.id.input_email);
        phone_textView = findViewById(R.id.input_phone);
        RadioGroup statusGroup = findViewById(R.id.status_group);
        int checkedId = statusGroup.getCheckedRadioButtonId();
        radioButton = findViewById(checkedId);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupInfo();
//                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void signupInfo(){
        boolean result_ok = true;

        String status = radioButton.getText().toString();

        String name = name_textView.getText().toString();
        String id = id_textView.getText().toString();
        String email = email_textView.getText().toString();
        String phone = phone_textView.getText().toString();

        User u = new User(name,id,phone,email,status);
        new ElasticSearchAuthentication.signUpUserTask().execute(u);
        Log.d("WTFWTF", "signupInfo:");

        // TODO: check if this user already has an account



//        return result_ok;
    }



//    public void addUser(View view, boolean result_ok){
//        if (result_ok){
//            // TODO: add user to database
//        }
//    }
//
//    public void addUser(View view) {
//        signupInfo();
//
//
//    }
}
