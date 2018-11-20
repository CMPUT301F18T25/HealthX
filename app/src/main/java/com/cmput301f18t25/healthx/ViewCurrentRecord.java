package com.cmput301f18t25.healthx;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class ViewCurrentRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_a_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        String title = bundle.getString("Title");
        String comment = bundle.getString("Comment");
        String date = bundle.getString("Date");
//        ElasticSearchRecordController.GetRecordsTask getRecordTask = new ElasticSearchRecordController.GetRecordsTask();
//        Record record = null;
//        try {
//            record = getRecordTask.execute(title,comment,date).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//

        TextView rtitle = findViewById(R.id.record_title);
        rtitle.setText(title);
        TextView rtime = findViewById(R.id.recordTimestamp);
        rtime.setText(date);
        TextView rcomment = findViewById(R.id.record_comment);
        rcomment.setText(comment);
    }

}
