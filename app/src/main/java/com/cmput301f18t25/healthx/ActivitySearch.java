/*
 * Class Name: ActivitySearch
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This is the activity that allows the user to search by keyword/bodylocation/geolocation.
 *
 * @author Ivan
 * @author Dhruba
 * @version 1.0
 *
 */

public class ActivitySearch extends AppCompatActivity  {
    private RecyclerView sRecyclerView;
    private RecyclerView.Adapter sAdapter;
    private RecyclerView.LayoutManager sLayoutManager;
    private ArrayList<Problem> problemList = new ArrayList<Problem>();
    private ArrayList<Record> recordList = new ArrayList<Record>();
    private ArrayList<Object> searchResults = new ArrayList<>();
    private ArrayList<Problem> userProblems = new ArrayList<>();
    private ArrayList<Record> userRecords = new ArrayList<>();
    private Object searchType;
    ProblemList probSingleton = ProblemList.getInstance();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        final Spinner searchSpin = findViewById(R.id.search_spin);
        userId = probSingleton.getUser().getId();

        ArrayAdapter<CharSequence> spinAdp = ArrayAdapter.createFromResource(this,
                R.array.searchOptions, android.R.layout.simple_spinner_item);
        spinAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpin.setAdapter(spinAdp);

        final EditText inputView = findViewById(R.id.search_text);
        final EditText latitudeView = findViewById(R.id.search_latitude);
        final EditText longitudeView = findViewById(R.id.search_longitude);
        final EditText bodyLocationView = findViewById(R.id.search_body_location);
        final LinearLayout extraSearchView = findViewById(R.id.search_extra);

        sRecyclerView = findViewById(R.id.recycler_search);
        sRecyclerView.setHasFixedSize(true);
        sLayoutManager = new LinearLayoutManager(ActivitySearch.this);
        sRecyclerView.setLayoutManager(sLayoutManager);

        searchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                searchType = parent.getItemAtPosition(pos);
                if(searchType.toString().equals("Geo-location")){
                    extraSearchView.setVisibility(View.VISIBLE);
                    bodyLocationView.setVisibility(View.GONE);
                    latitudeView.setVisibility(View.VISIBLE);
                    longitudeView.setVisibility(View.VISIBLE);

                } else if (searchType.toString().equals("Body Location")){
                    extraSearchView.setVisibility(View.VISIBLE);
                    latitudeView.setVisibility(View.GONE);
                    longitudeView.setVisibility(View.GONE);
                    bodyLocationView.setVisibility(View.VISIBLE);
                } else {
                    extraSearchView.setVisibility(View.GONE);
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

                    switch (searchType.toString()) {
                        case "Geo-location":
                            /** search by geo location */
                            try {
                                String latitude = latitudeView.getText().toString();
                                String longitude = longitudeView.getText().toString();
                                recordList = new ElasticSearchRecordController.SearchRecordsTask().execute(keyword, latitude, longitude).get();
                                problemList = new ElasticSearchProblemController.SearchProblemsFromRecordsTask().execute(recordList).get();
                                ArrayList<Problem> uniqueProblems = new ArrayList<>();
                                ArrayList<String> uniqueProblemIds = new ArrayList<>();
                                for (int i = 0; i < problemList.size(); i++) {
                                    if (!uniqueProblemIds.contains(problemList.get(i).getId())) {
                                        uniqueProblemIds.add(problemList.get(i).getId());
                                        uniqueProblems.add(problemList.get(i));
                                    }
                                }

                                for (int i = 0; i < uniqueProblems.size(); i++){
                                    if(uniqueProblems.get(i).getUserId().equals(userId)){
                                        userProblems.add(uniqueProblems.get(i));
                                    }
                                }

                                for (int i = 0; i < recordList.size(); i++){
                                    for (int j = 0; j < userProblems.size(); j++) {
                                        if (recordList.get(i).getProblemID().equals(userProblems.get(j).getId())){
                                            userRecords.add(recordList.get(i));
                                        }
                                    }
                                }

                                searchResults.addAll(userProblems);
                                searchResults.addAll(userRecords);


                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;

                        case "Body Location":
                            /** search by geo location */
                            try {
                                String bodyLocation = bodyLocationView.getText().toString();

                                problemList = new ElasticSearchProblemController.SearchProblemsTask().execute(keyword, bodyLocation).get();
                                for (int i = 0; i < problemList.size(); i++){
                                    if (problemList.get(i).getUserId().equals(userId)){
                                        userProblems.add(problemList.get(i));
                                    }
                                }

                                searchResults.addAll(userProblems);

                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            break;
                        default:
                            /** search by keywords */
                            try {
                                problemList = new ElasticSearchProblemController.SearchProblemsTask().execute(keyword).get();

                                for (int i = 0; i < problemList.size(); i++){
                                    if (problemList.get(i).getUserId().equals(userId)){
                                        userProblems.add(problemList.get(i));
                                    }
                                }

                                recordList = new ElasticSearchRecordController.SearchRecordsTask().execute(keyword).get();
                                for (int i = 0; i < recordList.size(); i++){
                                    for (int j = 0; j < probSingleton.getProblemArray().size(); j++){
                                        if (recordList.get(i).getProblemID().equals(probSingleton.getElementByIndex(j).getId())){
                                            userRecords.add(recordList.get(i));
                                        }
                                    }

                                }
                                searchResults.addAll(userProblems);
                                searchResults.addAll(userRecords);

                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            break;
                    }

                    sAdapter = new ProblemRecordAdapter(searchResults);
                    sRecyclerView.setAdapter(sAdapter);

                }

                return false;
            }

        });

    }


    public void toViewProblem(View view) {
        finish();
    }
}

