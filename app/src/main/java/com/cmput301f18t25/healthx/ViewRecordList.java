package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import java.util.concurrent.ExecutionException;

public class ViewRecordList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView rRecyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private ArrayList<Record> recordList = new ArrayList<Record>();
    private  String problemId;
    private int position;
    private ProblemList mProblemList = ProblemList.getInstance();
    private OfflineBehaviour offline = OfflineBehaviour.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        Bundle bundle = this.getIntent().getExtras();
        problemId = bundle.getString("ProblemID");
        position = bundle.getInt("Position");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewRecordList.this,ActivityAddRecord.class);
                Bundle bundle = new Bundle();
                bundle.putString("ProblemID",problemId);
                bundle.putInt("Position", position);
                intent.putExtras(bundle); // pass the problemid to the addactivity
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            recordList = mProblemList.getRecordList(position);
        } else  {
            offline.synchronizeWithElasticSearch();
            try {
                recordList = new ElasticSearchRecordController.GetRecordsTask().execute(problemId).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("IVANLIM", "recordlist: " + String.valueOf(recordList.size()));
            mProblemList.addRecordListToProblem(position, recordList);
        }




    }

    @Override
    protected void onStart(){
        super.onStart();


//        offline.synchronizeWithElasticSearch();
        rRecyclerView = findViewById(R.id.recycler_list);
        rRecyclerView.setHasFixedSize(true);

        rLayoutManager = new LinearLayoutManager(this);
        rRecyclerView.setLayoutManager(rLayoutManager);
        rAdapter = new RecordListAdapter(mProblemList.getRecordList(position));
        rRecyclerView.setAdapter(rAdapter);


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
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view) {
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapViewActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("RecordList", (Serializable) recordList);
            intent.putExtra("BUNDLE",args);
            startActivity(intent);

        } else if (id == R.id.nav_edit) {
            Intent intent = new Intent(this, EditUserProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
