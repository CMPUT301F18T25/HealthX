package com.cmput301f18t25.healthx;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class OfflineSave {

    private static final String USRFILENAME = "users.sav";
    private static final String PROBLEMLISTFILENAME = "problemlist.sav";
    Context mContext;

    private OfflineSave(Context context) {
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

        }catch (FileNotFoundException e) {
            Toast.makeText(mContext, "Error, unable to load the files.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
        }
    }

    public void loadProblemListFile() {

    }

}
