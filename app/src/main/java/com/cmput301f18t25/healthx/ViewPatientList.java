/*
 * Class Name: ViewPatientList
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
public class ViewPatientList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<User> patientList = new ArrayList<User>();
    private String doctorID;
    TextView Uid;
    TextView Uname;
    TextView Uemail;
    TextView Uphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        User user = null;
        try {
            user = getUserTask.execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Uid = (TextView) header.findViewById(R.id.user_id);
        Uid.setText(id);
        Uname = (TextView)header.findViewById(R.id.user_name);
        Uname.setText(user.getName());
        Uemail = (TextView)header.findViewById(R.id.user_email);
        Uemail.setText(user.getEmail());
        Uphone = (TextView)header.findViewById(R.id.user_phone);
        Uphone.setText(user.getPhoneNumber());
        ImageView headerImage = header.findViewById(R.id.imageView);
        headerImage.setImageDrawable(getResources().getDrawable(R.drawable.doctor));
        doctorID = user.getId();
        try {
            patientList = new ElasticSearchUserController.GetPatientsTask().execute(doctorID).get();
        }catch (Exception e){

        }

        mRecyclerView = findViewById(R.id.recycler_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PatientListAdapter(patientList,this.getIntent());
        mRecyclerView.setAdapter(mAdapter);
        SwipeHelper swipeHelper = new SwipeHelper(this, mRecyclerView) {
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton("Delete", getResources().getColor(R.color.DeleteButtonColor),
                        new UnderlayButtonClickListener() {

                            public void onClick(int position) {
                                ElasticSearchUserController.DeletePatientTask deletePatientTask = new ElasticSearchUserController.DeletePatientTask();
                                deletePatientTask.execute(patientList.get(position));
                                patientList.remove(position);
                                mAdapter.notifyItemRemoved(position);

                            }
                        }
                ));


            };};

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                Intent intent = new Intent(ViewPatientList.this, ActivityAddPatient.class);
                bundle.putString("doctorID",doctorID);
                intent.putExtras(bundle); // pass the problemid to the addactivity

                startActivityForResult(intent, 10);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10){
            try {
                patientList = new ElasticSearchUserController.GetPatientsTask().execute(doctorID).get();
            }catch (Exception e){

            }
            mAdapter = new PatientListAdapter(patientList,this.getIntent());
            mRecyclerView.setAdapter(mAdapter);

        }
        else if(resultCode == 15)
        {
            ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
            Bundle newBundle = data.getExtras();
            String id = newBundle.getString("username");

            try {
                User user = getUserTask.execute(id).get();

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view) {
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_map) {

        } else if (id == R.id.nav_code) {
            Bundle obundle = null;
            obundle = this.getIntent().getExtras();
            String Oid = obundle.getString("id");
            String Oemail = obundle.getString("email");

            Bundle bundle = new Bundle();
            bundle.putAll(obundle);
            Intent intent = new Intent(this, ActivityGenerateCode.class);
            intent.putExtras(bundle);
            startActivity(intent);

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
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
