package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityEditProblem extends AppCompatActivity {
    String title;
    String description;
    String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = this.getIntent().getExtras();
        EditText title_textView = (EditText) findViewById(R.id.title_input);
        DatePicker dateStarted_textView = findViewById(R.id.dateStarted_input);
        EditText description_textView = (EditText) findViewById(R.id.description_input);
        title = bundle.getString("title");
        description = bundle.getString("description");
        title_textView.setText(title);
        description_textView.setText(description);
        dateString = bundle.getString("date");
        dateStarted_textView.updateDate(Integer.valueOf(dateString.substring(0, 4)),
                Integer.valueOf(dateString.substring(5,7)),
                Integer.valueOf(dateString.substring(8,10)));


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

        // if clicked the save button,
        if (id == android.R.id.home) {
            Bundle bundle = this.getIntent().getExtras();
            Intent intent = new Intent(this, ViewProblemList.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if (id == R.id.save_button) {

            EditText title_textView = (EditText) findViewById(R.id.title_input);
            DatePicker dateStarted_textView = findViewById(R.id.dateStarted_input);
            EditText description_textView = (EditText) findViewById(R.id.description_input);


            String problemTitle = title_textView.getText().toString();
            Date selected = new Date(dateStarted_textView.getYear() - 1900,
                    dateStarted_textView.getMonth(), dateStarted_textView.getDayOfMonth());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String problemDate = format.format(selected);
            String problemDescription = description_textView.getText().toString();

            // Check if app is connected to a network.
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null == activeNetwork) {
                Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = getIntent().getExtras();
                Problem newProblem = new Problem(problemTitle, problemDescription, problemDate);
                Problem oldProblem = new Problem(title, description, dateString);
                Toast.makeText(this,problemDate,Toast.LENGTH_LONG).show();

                // NEED TO DELETE OLD PROBLEM
                ElasticSearchProblemController.DeleteProblemTask deleteProblemTask = new ElasticSearchProblemController.DeleteProblemTask();
                deleteProblemTask.execute(oldProblem);
                ElasticSearchProblemController.AddProblemTask addProblemTask = new ElasticSearchProblemController.AddProblemTask();
                addProblemTask.execute(newProblem);
                Intent intent = new Intent(ActivityEditProblem.this, ViewProblemList.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }



        }
        return super.onOptionsItemSelected(item);

    }

}
