package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class EditUserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner freq = (Spinner) findViewById(R.id.frequency_menu);


        ArrayAdapter<CharSequence> spinAdp = ArrayAdapter.createFromResource(this,
                R.array.frequency, android.R.layout.simple_spinner_item);
        spinAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freq.setAdapter(spinAdp);

        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
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
        Eid.setText(user.getUserId());
        TextView Ename = (TextView)findViewById(R.id.edit_name);
        Ename.setText(user.getName());
        TextView Ephone = (TextView)findViewById(R.id.edit_phone);
        Ephone.setText(user.getPhoneNumber());
        TextView Eemail = (TextView)findViewById(R.id.edit_email);
        Eemail.setText(user.getEmail());


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
            Intent intent = new Intent(this, ViewProblemList.class);
            startActivity(intent);
        }
        if (id == R.id.save_button) {
            Bundle bundle = null;
            bundle = this.getIntent().getExtras();
            String Bid = bundle.getString("id");
            String Bemail = bundle.getString("email");
            ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
            User user = null;
            try {
                user = getUserTask.execute(Bid,Bemail).get();
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

            user.setEmail(EEMAIL);
            user.setName(ENAME);
            user.setReminderFrequency(frequency);
            user.setPhoneNumber(EPHONE);
            ElasticSearchUserController.UpdateUserTask updateUserTask = new ElasticSearchUserController.UpdateUserTask();
            updateUserTask.execute(user);
            Toast.makeText(this, "Profile Edited", Toast.LENGTH_SHORT).show();
            bundle = null;
            bundle.putString("id",user.getUserId());
            bundle.putString("email",user.getEmail());
            Intent intent = new Intent(this, ViewProblemList.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
