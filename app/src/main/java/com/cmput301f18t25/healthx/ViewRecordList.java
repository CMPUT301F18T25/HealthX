package com.cmput301f18t25.healthx;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewRecordList extends AppCompatActivity{

    private RecyclerView rRecyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private ArrayList<Record> recordList = new ArrayList<Record>();
    private  String problemId;
    private boolean isDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Bundle bundle = this.getIntent().getExtras();
        problemId = bundle.getString("ProblemID");
        //isDoctor = bundle.getBoolean("isDoctor");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewRecordList.this,ActivityAddRecord.class);
                intent.putExtras(bundle); // pass the problemid to the addactivity
                startActivity(intent);
                //startActivityForResult(intent, 1);

            }
        });

    }


    @Override
    protected void onStart(){
        super.onStart();
        try {
            recordList = new ElasticSearchRecordController.GetRecordsTask().execute(problemId).get();
        }catch (Exception e){

        }
        rRecyclerView = findViewById(R.id.recycler_list);
        rRecyclerView.setHasFixedSize(true);

        rLayoutManager = new LinearLayoutManager(this);
        rRecyclerView.setLayoutManager(rLayoutManager);
        rAdapter = new RecordListAdapter(recordList);
        rRecyclerView.setAdapter(rAdapter);
        if (!isDoctor){

        }
        SwipeHelper swipeHelper = new SwipeHelper(this, rRecyclerView) {
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton("Delete", getResources().getColor(R.color.DeleteButtonColor),
                        new UnderlayButtonClickListener() {

                            public void onClick(int position) {
                                ElasticSearchRecordController.DeleteRecordTask deleteRecordTask = new ElasticSearchRecordController.DeleteRecordTask();
                                deleteRecordTask.execute(recordList.get(position));
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
                                intent.putExtras(bundle);
                                startActivity(intent);
                                }
                        }
                ));
            }
        };

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            finish();
        }
        else if (id == R.id.map_button){
            Toast toast = Toast.makeText(getApplicationContext(), "View map" , Toast.LENGTH_SHORT);
            toast.show();

        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


}
