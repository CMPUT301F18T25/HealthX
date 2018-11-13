package com.cmput301f18t25.healthx.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f18t25.healthx.Constants;
import com.cmput301f18t25.healthx.User;
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
    private static JestDroidClient client = null;


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

        @Override
        protected Void  doInBackground(User... users) {
            verifySettings();
            User u = users[0]; // references the user that we need
            Log.d("WTFWTF", String.valueOf(users.length));
            Index index = new Index.Builder(u).index("Healthx").type("User").build();
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
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(Constants.TEST_ELASTIC_SEARCH_URL);
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client =  (JestDroidClient) factory.getObject();
        }
    }

}
