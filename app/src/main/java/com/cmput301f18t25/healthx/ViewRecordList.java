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
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.List;

public class ViewRecordList extends AppCompatActivity {

    private RecyclerView rRecyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private ArrayList<Record> recordList = new ArrayList<Record>();
    private String problemId;
    private boolean isDoctor;
    private int position;
    private ProblemList mProblemList = ProblemList.getInstance();
    private OfflineBehaviour offline = OfflineBehaviour.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle bundle = this.getIntent().getExtras();
        problemId = bundle.getString("ProblemID");
        position = bundle.getInt("Position");


        //isDoctor = bundle.getBoolean("isDoctor");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewRecordList.this, ActivityAddRecord.class);
                intent.putExtras(bundle); // pass the problemid to the addactivity
                startActivityForResult(intent, 10);
                //startActivityForResult(intent, 1);

            }
        });
        try {
            recordList = new ElasticSearchRecordController.GetRecordsTask().execute(problemId).get();
        } catch (Exception e) {
//=======
//                Bundle bundle = new Bundle();
//                bundle.putString("ProblemID",problemId);
//                bundle.putInt("Position", position);
//                intent.putExtras(bundle); // pass the problemid to the addactivity
//                startActivity(intent);
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork == null) {
//            recordList = mProblemList.getRecordList(position);
//        } else  {
//            offline.synchronizeWithElasticSearch();
//            try {
//                recordList = new ElasticSearchRecordController.GetRecordsTask().execute(problemId).get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.d("IVANLIM", "recordlist: " + String.valueOf(recordList.size()));
//            mProblemList.addRecordListToProblem(position, recordList);
//        }
//
//
//
//
//    }
//
//    @Override
//    protected void onStart(){
//        super.onStart();
//>>>>>>> master


//        offline.synchronizeWithElasticSearch();
        }
        rRecyclerView = findViewById(R.id.recycler_list);
        rRecyclerView.setHasFixedSize(true);

        rLayoutManager = new LinearLayoutManager(this);
        rRecyclerView.setLayoutManager(rLayoutManager);
//            rAdapter = new RecordListAdapter(mProblemList.getRecordList(position));
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
        Log.d("CWei", "OAR called");
        if(resultCode == 10)
        {
            try {
                recordList = new ElasticSearchRecordController.GetRecordsTask().execute(problemId).get();
                //Log.d("CWei", String.valueOf(recordList.size()));

            } catch (Exception e) {

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
            finish();
        }
        else if (id == R.id.map_button){
            Toast toast = Toast.makeText(getApplicationContext(), "View map" , Toast.LENGTH_SHORT);
            toast.show();

        }
        else if (id == R.id.slideShow_button){
            Toast toast = Toast.makeText(getApplicationContext(), "View Slide Show" , Toast.LENGTH_SHORT);
            toast.show();
//=======
//        //noinspection SimplifiableIfStatement
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_view) {
//            // Handle the camera action
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_map) {
//            Intent intent = new Intent(this, MapViewActivity.class);
//            Bundle args = new Bundle();
//            args.putSerializable("RecordList", (Serializable) recordList);
//            intent.putExtra("BUNDLE",args);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_edit) {
//            Intent intent = new Intent(this, EditUserProfile.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_logout) {
//>>>>>>> master
        //noinspection SimplifiableIfStatement

        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


}
