/*
 * Class Name: EditUserProfile
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
/**
 * This is the activity that allows the user to edit his profile.
 *
 *
 * @author Cecilia
 * @author Ivan
 * @author Aida
 * @version 1.0
 *
 */
public class EditUserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        assert (bundle != null);
        String id = bundle.getString("id");
        String email = bundle.getString("email");
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        User user = null;
        try {
            user = getUserTask.execute(id,email).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TextView Eid = (TextView)findViewById(R.id.edit_id);
        Eid.setText(user.getUsername());
        TextView Ename = (TextView)findViewById(R.id.edit_name);
        Ename.setText(user.getName());
        TextView Ephone = (TextView)findViewById(R.id.edit_phone);
        Ephone.setText(user.getPhoneNumber());
        TextView Eemail = (TextView)findViewById(R.id.edit_email);
        Eemail.setText(user.getEmail());


        Spinner freq = (Spinner) findViewById(R.id.frequency_menu);
        String userFreq = user.getReminderFrequency();

        List<String> freqList =  new ArrayList<String>();
        freqList.add(userFreq);
        String[] FreqList_all = getResources().getStringArray(R.array.frequency);
        for (int i=0;i < FreqList_all.length;i++){
            String freqItem = FreqList_all[i];
            if (!freqList.contains(freqItem)){
                freqList.add(freqItem);
            }

        }
        // Set adapter for spinner
        ArrayAdapter<String> spinAdp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,freqList);
        spinAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freq.setAdapter(spinAdp);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);

        return true;
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
        if (id == R.id.save_button) {
            /** if finish editing, we update the user info*/
            Bundle bundle = null;
            bundle = this.getIntent().getExtras();
            String Bid = bundle.getString("id");
            String Bemail = bundle.getString("email");
            ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
            User user = null;
            try {
                user = getUserTask.execute(Bid).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            Spinner freq = (Spinner)findViewById(R.id.frequency_menu);
            String frequency = String.valueOf(freq.getSelectedItem());

            TextView Ename = (TextView)findViewById(R.id.edit_name);
            String ENAME = Ename.getText().toString();
            TextView Ephone = (TextView)findViewById(R.id.edit_phone);
            String EPHONE = Ephone.getText().toString();
            TextView Eemail = (TextView)findViewById(R.id.edit_email);
            String EEMAIL = Eemail.getText().toString();
            String status = user.getStatus();

            //user.setReminderFrequency(frequency);
            User newUser = new User(ENAME,Bid,EPHONE,EEMAIL,status,frequency);
            newUser.setId(user.getId());
            ElasticSearchUserController.DeleteUserTask deleteUserTask = new ElasticSearchUserController.DeleteUserTask();
            deleteUserTask.execute(user);

            ElasticSearchUserController.UpdateUserTask updateUserTask = new ElasticSearchUserController.UpdateUserTask();
            updateUserTask.execute(newUser);

            Toast.makeText(this, "Profile Edited", Toast.LENGTH_SHORT).show();
            Bundle newBundle = new Bundle();

            newBundle.putString("username",newUser.getUsername());
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            Intent intent = new Intent();
            intent.putExtras(newBundle);
            setResult(15,intent);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
