/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Delete;

public class ActivityViewPatientProblem extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Problem> problemList = new ArrayList<Problem>();
    private ProblemList mProblemList = ProblemList.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.care_provider_view_problem);

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
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        User user = null;
        try {
            user = getUserTask.execute(id,email).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView Uid = (TextView) header.findViewById(R.id.user_id);
        Uid.setText(id);
        TextView Uname = (TextView)header.findViewById(R.id.user_name);
        Uname.setText(user.getName());
        TextView Uemail = (TextView)header.findViewById(R.id.user_email);
        Uemail.setText(user.getEmail());
        TextView Uphone = (TextView)header.findViewById(R.id.user_phone);
        Uphone.setText(user.getPhoneNumber());
        ImageView headerImage = header.findViewById(R.id.imageView);
        headerImage.setImageDrawable(getResources().getDrawable(R.drawable.doctor));



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();


    }
    @Override
    protected void onStart(){
        super.onStart();
        try {
            Bundle bundle = null;
            bundle = this.getIntent().getExtras();
            String email = bundle.getString("PatientEmail");
            Toast.makeText(this,email,Toast.LENGTH_LONG).show();
            String id = bundle.getString("PatientId");
            ElasticSearchUserController.GetUserTask getUserTaskTest = new ElasticSearchUserController.GetUserTask();
            User patient = null;
            try {
                patient = getUserTaskTest.execute(id,email).get();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //String userId = mProblemList.getUser().getId();
            //Log.d("IVANLIM", userId);
            LinearLayout card = findViewById(R.id.OnePatient);
            String color_string = "#c3b1e2";
            int myColor = Color.parseColor(color_string);
            card.setBackgroundColor(myColor);
            TextView pName = findViewById(R.id.patientName);
            TextView pUserId = findViewById(R.id.patientID);
            TextView pUserEmail = findViewById(R.id.patientEmail);
            TextView pUserPhone = findViewById(R.id.patientPhone);
            pName.setText(patient.getName());
            pUserId.setText(patient.getUsername());
            pUserEmail.setText(patient.getEmail());
            pUserPhone.setText(patient.getPhoneNumber());
            String userId = patient.getId();
            problemList = new ElasticSearchProblemController.GetProblemsTask().execute(userId).get();
        }catch (Exception e){


        }
        mRecyclerView = findViewById(R.id.recycler_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProblemListAdapter(problemList);
        mRecyclerView.setAdapter(mAdapter);


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

        } else if (id == R.id.nav_edit) {
            Bundle obundle = null;
            obundle = this.getIntent().getExtras();
            String Oid = obundle.getString("id");
            String Oemail = obundle.getString("email");

            Bundle bundle = new Bundle();
            bundle.putString("id",Oid);
            bundle.putString("email",Oemail);
            Intent intent = new Intent(this, EditUserProfile.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, Login.class);

            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
