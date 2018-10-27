package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public boolean signupInfo(){
        boolean result_ok = true;
        EditText id_textView = findViewById(R.id.input_id);
        EditText name_textView = findViewById(R.id.input_name);
        EditText email_textView = findViewById(R.id.input_email);
        EditText phone_textView = findViewById(R.id.input_phone);

        RadioGroup statusGroup = findViewById(R.id.status_group);
        int checkedId = statusGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(checkedId);
        String status = radioButton.getText().toString();

        String name = name_textView.getText().toString();
        String id = id_textView.getText().toString();
        String email = email_textView.getText().toString();
        String phone = phone_textView.getText().toString();

        // TODO: check if this user already has an account

        User user = new User(name,id,phone,email,status);

        return result_ok;
    }

    public void addUser(View view, boolean result_ok){
        if (result_ok){
            // TODO: add user to database
        }
    }
}
