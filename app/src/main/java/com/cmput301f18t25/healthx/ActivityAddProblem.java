package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityAddProblem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        EditText title = (EditText) findViewById(R.id.title_input);
        DatePicker date = findViewById(R.id.dateStarted_input);
        EditText description = (EditText) findViewById(R.id.description_input);


        String problemTitle = title.getText().toString();
        Date selected = new Date(date.getYear() - 1900, date.getMonth(), date.getDayOfMonth());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate = format.format(selected);
        String problemDes = description.getText().toString();
        // if clicked the save button,
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, ViewProblemList.class);
            startActivity(intent);
        }
        if (id == R.id.save_button) {
            Problem newProblem = new Problem(problemTitle,problemDes,selectedDate);
            Toast.makeText(this,selectedDate,Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);

    }

}
