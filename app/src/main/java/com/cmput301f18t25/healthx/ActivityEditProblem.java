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
import java.util.concurrent.ExecutionException;

public class ActivityEditProblem extends AppCompatActivity {
    String title;
    String description;
    String dateString;
    String userId;
    Problem oldProblem;
    Problem newProblem;
    int problemPositon;
    private ProblemList mProblemList = ProblemList.getInstance();
    private OfflineBehaviour offline = OfflineBehaviour.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        EditText title_textView = findViewById(R.id.title_input);
        DatePicker dateStarted_textView = findViewById(R.id.dateStarted_input);
        EditText description_textView = findViewById(R.id.description_input);
        oldProblem = (Problem) bundle.getSerializable("problem");
        title = oldProblem.getTitle();
        description = oldProblem.getDescription();
        dateString = oldProblem.getDate();
        //userId = oldProblem.getId();
        userId = oldProblem.getUserId();
        problemPositon = bundle.getInt("position");
        title_textView.setText(title);
        description_textView.setText(description);
        dateStarted_textView.updateDate(Integer.valueOf(dateString.substring(0, 4)),
                Integer.valueOf(dateString.substring(5, 7)) - 1,
                Integer.valueOf(dateString.substring(8, 10)));


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
//<<<<<<< HEAD
            if (id == android.R.id.home) {
                Intent intent = new Intent();
                setResult(10,intent);
                finish();
            }
//=======
//            Bundle bundle = this.getIntent().getExtras();
//            Intent intent = new Intent(this, ViewProblemList.class);
//            intent.putExtras(bundle);
//            startActivity(intent);
//>>>>>>> master
        }
        if (id == R.id.save_button) {

            EditText title_textView = findViewById(R.id.title_input);
            DatePicker dateStarted_textView = findViewById(R.id.dateStarted_input);
            EditText description_textView = findViewById(R.id.description_input);


            String problemTitle = title_textView.getText().toString();
            Date selected = new Date(dateStarted_textView.getYear() - 1900,
                    dateStarted_textView.getMonth(), dateStarted_textView.getDayOfMonth());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String problemDate = format.format(selected);
            String problemDescription = description_textView.getText().toString();
//            Problem newProblem = new Problem(problemTitle, problemDescription, problemDate, userId);

            // Check if app is connected to a network.
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null == activeNetwork) {
//                mProblemList.removeProblemFromList(problemPositon);
////                mProblemList.addToProblemList(newProblem);
////                offline.addItem(oldProblem, "DELETE");
////                offline.addItem(newProblem, "ADD");
                Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
//                finish();
            } else {

                Bundle bundle = getIntent().getExtras();

                String pID = oldProblem.getId();
                ElasticSearchProblemController.DeleteProblemTask deleteProblemTask = new ElasticSearchProblemController.DeleteProblemTask();
                deleteProblemTask.execute(oldProblem);


                newProblem = new Problem(problemTitle, problemDescription, problemDate, userId);
                newProblem.setId(pID);
                ElasticSearchProblemController.UpdateProblemTask updateProblemTask = new ElasticSearchProblemController.UpdateProblemTask();
                updateProblemTask.execute(newProblem);
                mProblemList.addToProblemList(newProblem);
                Log.d("CWei",oldProblem.getId()+ " "+oldProblem.getTitle());
                Log.d("CWei",newProblem.getId()+ " "+newProblem.getTitle());

                try {
                    Thread.sleep(1000);                 //1000 milliseconds is one second.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                Intent intent = new Intent();
                setResult(10,intent);
                finish();


            }



        }
        return super.onOptionsItemSelected(item);

    }

}
