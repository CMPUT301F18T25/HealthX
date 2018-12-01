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


public class OfflineSave {

    private static final String USRFILENAME = "users.sav";
    private static final String PROBLEMLISTFILENAME = "problemlist.sav";
    Context mContext;

    public OfflineSave(Context context) {
        this.mContext = context;
    }

    public void LoadFiles() {
        loadUsersFile();
        loadProblemListFile();
    }

    public void loadUsersFile() {
        try {
            FileInputStream fis = mContext.openFileInput(USRFILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            User user = gson.fromJson(in, new TypeToken<User>() {
            }.getType());
            fis.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(mContext, "Error, unable to load the files.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
        }
    }

    public void loadProblemListFile() {

    }

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
        try {
            FileOutputStream fos = mContext.openFileOutput(USRFILENAME, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(user, bufferedWriter);
            bufferedWriter.flush();
            fos.close();
            Log.d("Dhruba", "saveUserToFile: success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User loadUserFromFile() {
        User newuser = new User();
        newuser.setStatus("null");
        try {
            FileInputStream fis = mContext.openFileInput(USRFILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type user = new TypeToken<User>(){}.getType();
            newuser.setUser((User) gson.fromJson(bufferedReader, user));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if ("null".compareTo(newuser.getStatus()) != 0) {
            return newuser;
        } else {
            return null;
        }
    }
}
