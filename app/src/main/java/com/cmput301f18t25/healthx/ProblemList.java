/*
 * Class Name: ProblemList
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This is the entity class for the problemList singleton.
 *
 * @author Dhrub
 * @author Cecilia
 * @author Aida
 * @author Ajay
 * @version 1.0
 *
 */

public class ProblemList {
    private static ProblemList instance;


    private static ArrayList<Problem> problemArray;
    private static User user;

    public static ProblemList getInstance() {
        if (instance == null) {
            instance = new ProblemList();
        }

        return instance;
    }



    private ProblemList() {
        problemArray = new ArrayList<Problem>();

    }


    public ArrayList<Problem> getProblemArray() {
        sortArray();
        return problemArray;
    }

    public void setProblemArray(ArrayList<Problem> array) {

        problemArray = array;
        sortArray();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User u) {
        ProblemList.user = u;
    }

    public void addToProblemList(Problem problem) {

        problemArray.add(problem);
        sortArray();
    }

    // Edits a specific problem -- Note the function takes the index of the old problem and a new problem object
    public void EditProblem(int index, Problem e) {
        problemArray.set(index, e);
        sortArray();
    }

    public int getListCount() {
        return problemArray.size();
    }

    public Problem getElementByIndex(int index) {
        return problemArray.get(index);

    }

    public void removeProblemFromList(int index) {
        problemArray.remove(index);
        sortArray();
    }


    public void sortArray() {
        Collections.sort(problemArray, new Comparator<Problem>() {
            @Override
            public int compare(Problem t1, Problem t2) {
                return t2.getDate().compareTo(t1.getDate());
            }
        });
    }


    public void sortRecordArray(int i) {
        Collections.sort(problemArray.get(i).recordArray, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
    }

    public void addToRecordToProblem(int index, Record record) {
        problemArray.get(index).recordArray.add(record);
    }


    public void addRecordListToProblem(int position, ArrayList<Record> recordList) {
        Log.d("IVANLIM", problemArray.get(position).getTitle());
        problemArray.get(position).recordArray = new ArrayList<>(recordList);
        for (Record r : problemArray.get(position).recordArray) {
            Log.d("IVANLIM", r.getTitle());
        }
    }

    public ArrayList<Record> getRecordList(int position) {
        sortRecordArray(position);
        return problemArray.get(position).recordArray;
    }

    public void removeRecord(int problemPosition, int recordPosition) {
        problemArray.get(problemPosition).recordArray.remove(recordPosition);
        sortRecordArray(problemPosition);
    }

    public void addRecord(int problemPosition, Record r) {
        problemArray.get(problemPosition).recordArray.add(r);
        sortRecordArray(problemPosition);
    }

    public Record getRecord(int problemPosition, int recordPosition) {
        return problemArray.get(problemPosition).recordArray.get(recordPosition);
    }

    public int getPositionByProblemId(String id) {
        for (int i = 0; i < problemArray.size(); i++) {
            if (problemArray.get(i).getId().compareTo(id) == 0) {
                return i;
            }
        }
       return -1;
    }
}

