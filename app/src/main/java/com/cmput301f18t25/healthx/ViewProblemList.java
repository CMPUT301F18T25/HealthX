package com.cmput301f18t25.healthx;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Delete;

public class ViewProblemList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Problem> problemList = new ArrayList<Problem>();
    private ProblemList mProblemList = ProblemList.getInstance();
    OfflineSave offlineSave;
    private boolean isDoctor;
    TextView Uid;
    TextView Uname;
    TextView Uemail;
    TextView Uphone;
    String userId;
    User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler);
        offlineSave = new OfflineSave(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        String email = bundle.getString("email");

//        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        try {
            userId = mProblemList.getUser().getId();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (offlineSave.checkNetworkStatus()) {
            ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
            try {

                Log.d("IVANLIM", userId);
                problemList = new ElasticSearchProblemController.GetProblemsTask().execute(userId).get();
                mProblemList.setProblemArray(problemList);
                try {
                    user = getUserTask.execute(id,email).get();
                    if (user.getStatus().equals("Care Provider")){
                        isDoctor = true;
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }catch (Exception ignored){
            }
        }
        else {
            offlineSave.loadProblemList(userId);
            problemList = mProblemList.getProblemArray();
            user = mProblemList.getUser();
        }

        mRecyclerView = findViewById(R.id.recycler_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProblemListAdapter(problemList,isDoctor);
        mRecyclerView.setAdapter(mAdapter);
        SwipeHelper swipeHelper = new SwipeHelper(this, mRecyclerView) {
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton("Delete", getResources().getColor(R.color.DeleteButtonColor),
                        new UnderlayButtonClickListener() {

                            public void onClick(int position) {
                                ElasticSearchProblemController.DeleteProblemTask deleteProblemTask = new ElasticSearchProblemController.DeleteProblemTask();
                                deleteProblemTask.execute(problemList.get(position));
                                problemList.remove(position);
                                mProblemList.removeProblemFromList(position);
                                mAdapter.notifyItemRemoved(position);


                            }
                        }
                ));

                underlayButtons.add(new UnderlayButton("Edit", getResources().getColor(R.color.EditButtonColor),
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Problem problem = problemList.get(pos);
                                Intent intent = new Intent(ViewProblemList.this, ActivityEditProblem.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("problem", mProblemList.getElementByIndex(pos));

                                intent.putExtras(bundle);
                                startActivityForResult(intent,10);

                            }
                        }
                ));
            }
        };
 if (user != null) {
     Uid = (TextView) header.findViewById(R.id.user_id);
     Uid.setText(id);
     Uname = (TextView)header.findViewById(R.id.user_name);
     Uname.setText(user.getName());
     Uemail = (TextView)header.findViewById(R.id.user_email);
     Uemail.setText(user.getEmail());
     Uphone = (TextView)header.findViewById(R.id.user_phone);
     Uphone.setText(user.getPhoneNumber());
 }

        ImageView headerImage = header.findViewById(R.id.imageView);
        headerImage.setImageDrawable(getResources().getDrawable(R.drawable.patient));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bundle bundle = null;
                //bundle = ViewProblemList.this.getIntent().getExtras();
                Intent intent = new Intent(ViewProblemList.this, ActivityAddProblem.class);

                //intent.putExtras(bundle);

                startActivityForResult(intent,10);
            }
        });

//        String frequency = user.getReminderFrequency();
//
//        Log.d("CWei", frequency+"freq");
//        if (!frequency.equals("None")){
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY,9);
//            calendar.set(Calendar.MINUTE,0);
//            calendar.set(Calendar.SECOND,0);
//
//
//            if (calendar.getTime().compareTo(new Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1);
//            Intent intent = new Intent(this, Notification_receiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
//
//            if (frequency.equals("Everyday")){
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24 * 60 * 60 * 1000,pendingIntent);
//
//            }
//            else if (frequency.equals("Every week")){
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),7 * 24 * 60 * 60 * 1000,pendingIntent);
//
//            }
//            else if (frequency.equals("Every month")){
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),30 * 24 * 60 * 60 * 1000,pendingIntent);
//
//            }
//
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("CWei", "OAR called");
        Log.d("CWei", String.valueOf(resultCode));
//        ProblemList mProblemList = ProblemList.getInstance();
        if(resultCode == 10)
        {   //Log.d("CWei", "why");
            try {
                String userId = mProblemList.getUser().getId();
                problemList = new ElasticSearchProblemController.GetProblemsTask().execute(userId).get();
//                mProblemList.setProblemArray(problemList);
            }catch (Exception e){

            }
            //Log.d("CWei", "not");
            mAdapter = new ProblemListAdapter(mProblemList.getProblemArray(),isDoctor);
            mRecyclerView.setAdapter(mAdapter);
        }
        if(resultCode == 15)
        {   Log.d("CWei", "executed");
            ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
            Bundle newBundle = data.getExtras();
            String id = newBundle.getString("username");
            Log.d("CWei", id);
            try {
                User user = getUserTask.execute(id).get();
                Log.d("CWei", user.getName());
                Uid.setText(id);
                Uname.setText(user.getName());
                Uemail.setText(user.getEmail());
                Uphone.setText(user.getPhoneNumber());

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
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
        if (id == R.id.action_search){
            Intent intent = new Intent(this, ActivitySearch.class);
            startActivity(intent);
        }
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
//            Intent intent = new Intent(this, MapViewActivity.class);
//            startActivity(intent);
            Toast toast = Toast.makeText(this, "Please Select a Problem to enable Map View", Toast.LENGTH_LONG);
            toast.show();

        } else if (id == R.id.nav_code) {
            Bundle obundle = null;
            obundle = this.getIntent().getExtras();
            Bundle bundle = new Bundle();
            bundle.putAll(obundle);
            Intent intent = new Intent(this, ActivityGenerateCode.class);
            intent.putExtras(bundle);
            startActivityForResult(intent,10);


        } else if (id == R.id.nav_edit) {
            Bundle obundle = null;
            obundle = this.getIntent().getExtras();
            String Oid = obundle.getString("id");

            Bundle bundle = new Bundle();
            bundle.putString("id",Oid);

            Intent intent = new Intent(this, EditUserProfile.class);
            intent.putExtras(bundle);
            startActivityForResult(intent,15);
        } else if (id == R.id.nav_logout) {
            //finish();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}