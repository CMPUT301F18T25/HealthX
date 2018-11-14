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
import android.widget.Toast;

import com.cmput301f18t25.healthx.Model.ElasticSearchAuthentication;

public class Signup extends AppCompatActivity {

    EditText id_textView, name_textView, email_textView, phone_textView;
    RadioButton radioButton;
    RadioGroup statusGroup;
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
        statusGroup = (RadioGroup) findViewById(R.id.status_group);


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupInfo();

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
        int checkedId = statusGroup.getCheckedRadioButtonId();
        radioButton =(RadioButton) findViewById(checkedId);
        String status = radioButton.getText().toString();
        String name = name_textView.getText().toString();
        String userid = id_textView.getText().toString();
        String email = email_textView.getText().toString();
        String phone = phone_textView.getText().toString();

        User u = new User(name,userid,phone,email,status);
        Log.d("Jerky", u.getName() + " " + u.getUserId() + " " + u.getPhoneNumber() + " " + u.getEmail() + " " + u.getStatus());

        new ElasticSearchAuthentication.signUpUserTask(this).execute(u);

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
