/*
 * Class Name: OfflineSave
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class OfflineSave {

    private static final String USRFILENAME = "users.sav";
    private static final String PROBLEMLISTFILENAME = "problemlist.sav";
    private ProblemList problemList = ProblemList.getInstance();
    Context mContext;
    private ArrayList<Problem> allproblems;
    UserList userList = UserList.getInstance();

    public OfflineSave(Context context) {
        this.mContext = context;
        this.allproblems = new ArrayList<>();
    }



    public void loadUsersFile() {
        try {
            FileInputStream fis = mContext.openFileInput(USRFILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
           ArrayList<User> ulist = gson.fromJson(in, new TypeToken<ArrayList<User>>() {
            }.getType());
           userList.SetUserList(ulist);
            fis.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(mContext, "Error, unable to load the files.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
        }

    }


    // function that checks network status. Returns True if network exists, false if not.
    public boolean checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {

            return false;
        } else {
            return true;
        }

    }

    public void saveUserToFile(User user) {
        loadUsersFile();
        userList.addToList(user);
        try {
            FileOutputStream fos = mContext.openFileOutput(USRFILENAME, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(userList.getUserlist(), bufferedWriter);
            bufferedWriter.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void loadTheProblemList() {
        try {
            FileInputStream fis = mContext.openFileInput(PROBLEMLISTFILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type prob = new TypeToken<ArrayList<Problem>>(){}.getType();
            allproblems = gson.fromJson(bufferedReader,prob);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadProblemList(String userId) {
        try {
            FileInputStream fis = mContext.openFileInput(PROBLEMLISTFILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type prob = new TypeToken<ArrayList<Problem>>(){}.getType();
            allproblems = gson.fromJson(bufferedReader,prob);
            ArrayList<Problem> sortedProblems = getProblemsByUserID(userId, allproblems);
            problemList.setProblemArray(sortedProblems);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<Problem> getProblemsByUserID(String userId, ArrayList<Problem> problems) {
        ArrayList<Problem> sortedProbs = new ArrayList<>();

        for (Problem p: problems) {

            if (p.getUserId().compareTo(userId) == 0) {
                sortedProbs.add(p);
            }
        }
        return sortedProbs;
    }

    public void saveProblemListToFile(Problem aproblem) {
        loadTheProblemList();
        allproblems.add(aproblem);
        try {
            FileOutputStream fos = mContext.openFileOutput(PROBLEMLISTFILENAME, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(allproblems, bufferedWriter);
            bufferedWriter.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User loadUserFromFile() {

        try {
            FileInputStream fis = mContext.openFileInput(USRFILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type user = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> userdata = gson.fromJson(bufferedReader, user);
            userList.SetUserList(userdata);

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userList.getUserlist().size() != 0) {

            return userList.getUserlist().get(userList.getUserlist().size()-1);
        }
        else {
            return null;
        }

    }

    public void saveRecordsToProblem(Problem problem) {
        try {
            loadTheProblemList();
            ArrayList<Problem> ppo = allproblems;
            for (int i = 0; i < ppo.size(); i++) {
                if (ppo.get(i).getId().compareTo(problem.getId())== 0) {
                    allproblems.set(i, problem);
                }
            }

            FileOutputStream fos = mContext.openFileOutput(PROBLEMLISTFILENAME, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(allproblems, bufferedWriter);
            bufferedWriter.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
