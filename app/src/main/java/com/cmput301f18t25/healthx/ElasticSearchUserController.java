package com.cmput301f18t25.healthx;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Handling elasticsearch user related queries
 */

public class ElasticSearchUserController {
    private static JestDroidClient client;

    // adds user to elasticsearch
    public static class AddUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();
            for (User user : users) {
                Index index = new Index.Builder(user).index("cmput301f18t25test").type("user").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    if (!result1.isSucceeded()) {
                        Log.i("Error", "Elasticsearch was not able to add user.");
                    }
                    // where is the client?
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the tweets");
                }

            }
            return null;
        }
    }

    public static class GetUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... users) {
//            "{n\"query\" : {\"term\" : { \"userID\" : \"hai\" }}}";
            verifySettings();
            User theUser = new User("", "", "", "", "");
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "                \"bool\" : {\n" +
                    "\"must\" : [\n"+ "{\"match\" : {\"userId\" : \""+ users[0]+ "\"}},\n" + "{\"match\" : {\"email\" : \""+ users[1]+"\"}}\n]\n}\n}\n}\n";

            // Build the query
            ArrayList<User> userArray = new ArrayList<User>();
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")
                    .addType("user")
                    .build();

            try {
                // gets result
                SearchResult result = client.execute(search);
//                SearchRes
                if (result.isSucceeded()) {
//                    Map user = result.getJsonMap();
                    String user = result.getJsonString();
                    Log.d("IVANLIM", user);

                    List<SearchResult.Hit<User, Void>> hits = result.getHits(User.class);
                    List<User> userList;
                    userList = result.getSourceAsObjectList(User.class);
                    userArray.addAll(userList);
                    theUser.cloneUser(userArray.get(0));


//                    }
                } else {
                    Log.i("IVANLIM", "The search query failed to find any user that matched.");
                }
            } catch (Exception e) {
                Log.i("IVANLIM", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return theUser;
        }
    }





    public static void verifySettings() {
        if (client == null) {

            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}