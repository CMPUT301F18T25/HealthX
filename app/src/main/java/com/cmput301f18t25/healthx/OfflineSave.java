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

//    public void LoadFiles() {
//        loadUsersFile();
//        loadProblemListFile();
//    }

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

//    public void loadProblemListFile() {
//
//    }

    // function that checks network status. Returns True if network exists, false if not.
    public boolean checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {
//            Toast.makeText(getApplicationContext(), "You are offline.", Toast.LENGTH_SHORT).show();
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
            Log.d("Dhruba", "saveUserToFile: success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        loadUserFromFile();
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
            Log.d("IVANLIM", allproblems.toString());
            ArrayList<Problem> sortedProblems = getProblemsByUserID(userId, allproblems);
            Log.d("IVANLIM", "loadProblemList: " + sortedProblems.toString());
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
        Log.d("IDKKK", "LETSOGOO");
        Log.d("IDKKK", userId);
        for (Problem p: problems) {
            Log.d("IDKKK", p.getId());
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
//        ArrayList<User> userArrayList = new ArrayList<>();
//        User newuser = new User();
//        newuser.setStatus("null");
        try {
            FileInputStream fis = mContext.openFileInput(USRFILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type user = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> userdata = gson.fromJson(bufferedReader, user);
            userList.SetUserList(userdata);
//            newuser.setUser((User) gson.fromJson(bufferedReader, user));
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userList.getUserlist().size() != 0) {
//            userList.setPreviousUser(userList.getUserlist().get(userList.getUserlist().size()-1));
            return userList.getUserlist().get(userList.getUserlist().size()-1);
        }
        else {
            return null;
        }

//        if ("null".compareTo(newuser.getStatus()) != 0) {
//            return newuser;
//        } else {
//            return null;
//        }
    }

    public void saveRecordsToProblem() {
        try {
            loadTheProblemList();
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
