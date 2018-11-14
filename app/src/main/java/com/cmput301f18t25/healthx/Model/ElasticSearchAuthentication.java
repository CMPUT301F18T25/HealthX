package com.cmput301f18t25.healthx.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f18t25.healthx.Constants;
import com.cmput301f18t25.healthx.Signup;
import com.cmput301f18t25.healthx.User;
import com.cmput301f18t25.healthx.ViewProblemList;



import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class ElasticSearchAuthentication {
    private static JestDroidClient client;


    /* TODO we need a function that adds users to the elastic search   */
    /*
    Read up on Documentation of AsyncTask for anyone looking at the code below.
    Asynctask creates a new thread sepearate from the UI thread that can handle
    asynchronous calls to elastic search. AsyncTask takes in three Parameters,
    which are Params, Progress, Result. Params are essentially paramaters that you pass into the
    execute function when you call it (ex: via: new signUpUserTask.execute(user), where the user
    param in the function call represents a newuser made through the signup activity.
     */
    public static class signUpUserTask extends AsyncTask<User, Void, Void> {
        private Context context;

        public signUpUserTask(Context mcontext) {
            context = mcontext;
        }

        @Override
        protected Void  doInBackground(User... users) {
            Log.d("Jerky", "Args has a lenght of " + String.valueOf(users.length));
            Log.d("Jerky", "doInBackground: called ");
            verifySettings();
            Log.d("Jerky", "doInBackground: after verify ");
            User u = users[0]; // references the user that we need
            Index index = new Index.Builder(u).index("cmput301f18t25test").type("User").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    Log.d("WTFWTF", "SUCCESSSSES ");
                    u.setID(result.getId()); // sets the id of the user
                }
            } catch (IOException e) {//do something here
                Log.d("WTFWTF", "DIDNT WORK");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent((Activity) context, ViewProblemList.class);
            context.startActivity(intent);
//            ((Activity) context).finish();

        }
    }



//    public static class logInUserTask extends  AsyncTask<User, Void, User> {
//        @Override
//        protected User doInBackground(User... users) {
//            User  user = new User();
//
//            return user;
//        }
//    }

    public static void verifySettings(){
        Log.d("Jerky", "verifySettings: executed");
        if (client == null) {
            Log.d("Jerky", "verifySettings: entedred client == null" );
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
            Log.d("Jerky", "verifySettings: builder config");
            DroidClientConfig config = builder.build();
            Log.d("Jerky", "verifySettings: builderbuild");
            JestClientFactory factory = new JestClientFactory();
            Log.d("Jerky", "verifySettings: factory");
            factory.setDroidClientConfig(config);
            Log.d("Jerky", "verifySettings: setdroidclientconfig");
            client =  (JestDroidClient) factory.getObject();
            Log.d("Jerky", "verifySettings: set cleint");
        }
    }

}
