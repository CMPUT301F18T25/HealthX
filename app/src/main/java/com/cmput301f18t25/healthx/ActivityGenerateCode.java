/*
 * Class Name: ActivityGenerateCode
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.ExecutionException;
public class ActivityGenerateCode extends AppCompatActivity {

    Button generate_btn;
    TextView code_output;
    User user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        generate_btn = (Button) findViewById(R.id.btn_generate);
        code_output = (TextView) findViewById(R.id.code_output);


        Bundle bundle = this.getIntent().getExtras();
        String Bid = bundle.getString("id");
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        try {
            user = getUserTask.execute(Bid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public void generateCode(View view) {

        code_output = (TextView) findViewById(R.id.code_output);
        String new_code = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(3,7));
        code_output.setText(new_code);

        RequestCode requestCode = new RequestCode(user.getUsername(),new_code);
        ElasticSearchUserController.AddRequestCodeTask addRequestCodeTask = new ElasticSearchUserController.AddRequestCodeTask();
        addRequestCodeTask.execute(requestCode);
//        try {
//            Thread.sleep(1000);                 //1000 milliseconds is one second.
//        } catch(InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }

    }
}
