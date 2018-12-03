/*
 * Class Name: ViewRecordList
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.List;
/**
 * This is the activity that allows the user to navigate the record list for a problem
 *
 * @author Ivan
 * @author Dhruba
 * @author Cecilia
 * @author Aida
 * @author Ajay
 * @version 1.0
 *
 */
public class ViewRecordList extends AppCompatActivity implements Serializable {

    private RecyclerView rRecyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private ArrayList<Record> recordList = new ArrayList<Record>();
    private String problemId;
    private boolean isDoctor;
    private int position;
    private ProblemList mProblemList = ProblemList.getInstance();
    private OfflineBehaviour offline = OfflineBehaviour.getInstance();
    private OfflineSave offlineSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        offline.synchronizeWithElasticSearch();
        offlineSave = new OfflineSave(this);

        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle bundle = this.getIntent().getExtras();
        problemId = bundle.getString("ProblemID");
        position = bundle.getInt("Position");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewRecordList.this, ActivityAddRecord.class);
                intent.putExtras(bundle); // pass the problemid to the addactivity
                startActivityForResult(intent, 10);


            }
        });

        if (offlineSave.checkNetworkStatus()) {
            try {
                recordList = new ElasticSearchRecordController.GetRecordsTask().execute(problemId).get();
                for(Record record: recordList){
                    mProblemList.addRecord(position, record);
                }
            } catch (Exception e) {

            }

        }
        else {

            recordList.addAll(mProblemList.getRecordList(position));
        }


        rRecyclerView = findViewById(R.id.recycler_list);
        rRecyclerView.setHasFixedSize(true);

        rLayoutManager = new LinearLayoutManager(this);
        rRecyclerView.setLayoutManager(rLayoutManager);

        rAdapter = new RecordListAdapter(recordList);
        rRecyclerView.setAdapter(rAdapter);
        SwipeHelper swipeHelper = new SwipeHelper(this, rRecyclerView) {
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton("Delete", getResources().getColor(R.color.DeleteButtonColor),
                        new UnderlayButtonClickListener() {

                            public void onClick(int position) {
                                ElasticSearchRecordController.DeleteRecordTask deleteRecordTask = new ElasticSearchRecordController.DeleteRecordTask();
                                deleteRecordTask.execute(recordList.get(position));
                                recordList.remove(position);
                                rAdapter.notifyItemRemoved(position);
                            }
                        }
                ));

                underlayButtons.add(new UnderlayButton("Edit", getResources().getColor(R.color.EditButtonColor),
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Record record = recordList.get(pos);
                                Intent intent = new Intent(ViewRecordList.this, ActivityEditRecord.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("record", record);
                                bundle.putInt("position",position);
                                bundle.putInt("recordPositon",pos);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 10);

                            }

                        }
                ));
            }
        };

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 10)
        {

            if (offlineSave.checkNetworkStatus()) {
                try {
                    recordList = new ElasticSearchRecordController.GetRecordsTask().execute(problemId).get();
                    for(Record record: recordList){
                        mProblemList.addRecord(position, record);
                    }
                } catch (Exception e) {

                }

            }
            else {

                recordList.addAll(mProblemList.getRecordList(position));
            }

            rAdapter = new RecordListAdapter(recordList);
            rRecyclerView.setAdapter(rAdapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent();
            setResult(10,intent);
            Log.i("CWei", "finished");
            finish();
        }
        else if (id == R.id.map_button){
            Toast toast = Toast.makeText(getApplicationContext(), "View map" , Toast.LENGTH_SHORT);
            toast.show();
            ArrayList<Record> withoutCpRecords = new ArrayList<>();
            for (Record r : recordList) {

                if (!r.isCPComment()) {
                    withoutCpRecords.add(r);
                }
            }
            Intent intent = new Intent(getApplicationContext(), MapViewActivity.class);
            intent.putExtra("Records", withoutCpRecords);
            startActivity(intent);

        }
        else if (id == R.id.slideShow_button){
            Toast toast = Toast.makeText(getApplicationContext(), "View Slide Show" , Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(getApplicationContext(),SlideShow.class);
            intent.putExtra("Records",recordList);
            startActivity(intent);
            
        }


        return super.onOptionsItemSelected(item);
    }

}
