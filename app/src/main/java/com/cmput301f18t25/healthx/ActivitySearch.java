/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActivitySearch extends AppCompatActivity  {
    private RecyclerView sRecyclerView;
    private RecyclerView.Adapter sAdapter;
    private RecyclerView.LayoutManager sLayoutManager;
    private ArrayList<Problem> problemList = new ArrayList<Problem>();
    private ArrayList<Record> recordList = new ArrayList<Record>();
    private ArrayList<Object> searchResults = new ArrayList<>();
    private Object searchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        final Spinner searchSpin = findViewById(R.id.search_spin);


        ArrayAdapter<CharSequence> spinAdp = ArrayAdapter.createFromResource(this,
                R.array.searchOptions, android.R.layout.simple_spinner_item);
        spinAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpin.setAdapter(spinAdp);

        final EditText inputView = findViewById(R.id.search_text);
        final EditText latitudeView = findViewById(R.id.search_latitude);
        final EditText longitudeView = findViewById(R.id.search_longitude);
        final EditText bodyLocationView = findViewById(R.id.search_body_location);

        sRecyclerView = findViewById(R.id.recycler_search);
        sRecyclerView.setHasFixedSize(true);
        sLayoutManager = new LinearLayoutManager(ActivitySearch.this);
        sRecyclerView.setLayoutManager(sLayoutManager);

        searchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                searchType = parent.getItemAtPosition(pos);
                if(searchType.toString().equals("Geo-location")){
                    bodyLocationView.setVisibility(View.GONE);
                    latitudeView.setVisibility(View.VISIBLE);
                    longitudeView.setVisibility(View.VISIBLE);
                } else if (searchType.toString().equals("Body Location")){
                    latitudeView.setVisibility(View.GONE);
                    longitudeView.setVisibility(View.GONE);
                    bodyLocationView.setVisibility(View.VISIBLE);
                } else {
                    bodyLocationView.setVisibility(View.GONE);
                    latitudeView.setVisibility(View.GONE);
                    longitudeView.setVisibility(View.GONE);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        inputView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER && event.getAction() == event.ACTION_UP) {

                    searchResults = new ArrayList<Object>();
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    String keyword = inputView.getText().toString();

                    if(searchType.toString().equals("Geo-location")) {
                        try {
                            String latitude = latitudeView.getText().toString();
                            String longitude = longitudeView.getText().toString();
                            // problemList = new ElasticSearchProblemController.SearchProblemsTask().execute(keyword,latitude,longitude).get();
                            recordList = new ElasticSearchRecordController.SearchRecordsTask().execute(keyword,latitude,longitude).get();
                            // searchResults.addAll(problemList);
                            searchResults.addAll(recordList);

                            sAdapter = new ProblemRecordAdapter(searchResults);
                            sRecyclerView.setAdapter(sAdapter);

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (searchType.toString().equals("Body Location")){
                        try {
                            String bodyLocation = bodyLocationView.getText().toString();
                            problemList = new ElasticSearchProblemController.SearchProblemsTask().execute(keyword, bodyLocation).get();
                            recordList = new ElasticSearchRecordController.SearchRecordsTask().execute(keyword).get();
                            searchResults.addAll(problemList);
                            searchResults.addAll(recordList);

                            sAdapter = new ProblemRecordAdapter(searchResults);
                            sRecyclerView.setAdapter(sAdapter);

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }else {
                        try {

                            problemList = new ElasticSearchProblemController.SearchProblemsTask().execute(keyword).get();
                            recordList = new ElasticSearchRecordController.SearchRecordsTask().execute(keyword).get();
                            searchResults.addAll(problemList);
                            searchResults.addAll(recordList);

                            sAdapter = new ProblemRecordAdapter(searchResults);
                            sRecyclerView.setAdapter(sAdapter);

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }

                return false;
            }

        });

    }


    public void toViewProblem(View view) {
        Intent intent = new Intent(this, ViewProblemList.class);
        startActivity(intent);
    }
}

