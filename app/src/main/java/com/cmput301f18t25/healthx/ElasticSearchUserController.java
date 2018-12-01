package com.cmput301f18t25.healthx;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.searchbox.client.JestResult;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Handling elasticsearch user related queries - UTILIZED THESE RESOURCES TO HELP ME
 */

public class ElasticSearchUserController {
    private static JestDroidClient client;

    // adds user to elasticsearch
    public static class AddUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();
            String userid = null;
            for (User user : users) {
                Index index = new Index.Builder(user).index("cmput301f18t25test").type("usernew1").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    if (!result1.isSucceeded()) {
                        Log.i("Error", "Elasticsearch was not able to add user.");
                    }
                    // where is the client?
                    else  {
                        userid = result1.getId();
                        Log.d("IVANLIM", userid);
                        user.setId(userid);
                        Index index1 = new Index.Builder(user).index("cmput301f18t25test").type("usernew2").build();
                        try {
                            DocumentResult result2 = client.execute(index1);
                            if (!result2.isSucceeded()) {
                                Log.i("Error", "doInBackground: error");
                            }
                        } catch (Exception e) {
                            Log.i("Error", "The application failed to build and send the tweets");
                        }
                    }
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
            String query = "{ \"query\" : { \"match\" :  { \"username\" : \""+ users[0] + "\"}}}";



            // Build the query
            String userId = null;
            ArrayList<User> userArray = new ArrayList<User>();
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")
                    .addType("usernew2")
                    .build();

            try {
                // gets result
                SearchResult result = client.execute(search);
//                DocumentResult docres = client.execute(search);
//                SearchRes
                if (result.isSucceeded()) {
//                    Map user = result.getJsonMap();
//                    String user = result.getJsonString();
//                    Log.d("IVANLIM", user);

//                    List<SearchResult.Hit<User, Void>> hits = result.getHits(User.class);

                    List<User> userList;
                    userList = result.getSourceAsObjectList(User.class);
                    userArray.addAll(userList);
                    theUser.cloneUser(userArray.get(0));
                    Log.d("IVANLIM", theUser.getId() );


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

    public static class AddRequestCodeTask extends AsyncTask<RequestCode, Void, Void> {

        @Override
        protected Void doInBackground(RequestCode... requestCodes) {
            verifySettings();
            String problemID;
            for (RequestCode rc : requestCodes){
                Index index = new Index.Builder(rc).index("cmput301f18t25test").type("newReqCodes").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    if (!result1.isSucceeded()) {
                        Log.i("Error", "Elasticsearch was not able to find user.");
                    } else {

                    }
                }
                catch (Exception e){
                    Log.i("Error", "The application failed to build and send the tweets");
                }
            }
            return null;

        }

    }

    public static class DeleteRequestCodeTask extends AsyncTask<RequestCode, Void, Void> {

        @Override
        protected Void doInBackground(RequestCode... requestCodes) {
            verifySettings();
            String query = "{\"query\" : { \"match\" : { \"user_code\" : \"" + requestCodes[0].getCode() + "\"}}}";
            DeleteByQuery delete = new DeleteByQuery.Builder(query).addIndex("cmput301f18t25test").addType("newReqCodes").build();
            try {
                client.execute(delete);
            } catch (Exception e) {
                Log.d("ElasticProblem", "The application failed to build and send the code");
            }

            return null;
        }

    }


    public static class GetRequestCodeTask extends AsyncTask<String, Void, ArrayList<RequestCode>> {
        @Override
        protected ArrayList<RequestCode> doInBackground(String... params) {
            verifySettings();
            ArrayList<RequestCode> requestCodes = new ArrayList<RequestCode>();

            String query = "{ \"query\" : { \"match\" :  { \"user_code\" : \""+ params[0] + "\"}}}";
            Search search = new Search.Builder(query)

                    .addIndex("cmput301f18t25test")
                    .addType("newReqCodes")
                    .build();
            try {
//                JestResult result = client.execute(search);
                JestResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<RequestCode> requestCodesList;
                    requestCodesList = result.getSourceAsObjectList(RequestCode.class);
                    requestCodes.addAll(requestCodesList);
                }
                else {
                    Log.d("IVANLIM", "Else caluse: ");
                }

            } catch (IOException e) {
                Log.d("Error", "Error in searching problems");
            }

            return requestCodes;
        }

    }

    public static class UpdateUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... search_parameters) {
            verifySettings();

            User user = search_parameters[0];
            String userId = user.getUsername();

            Index index = new Index.Builder(user).index("cmput301f18t25test").type("user").id(userId).build();


            try {
                DocumentResult result = client.execute(index);
                if (!result.isSucceeded()) {
                    Log.i("Error", "Elasticsearch was not able to update mood.");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send mood.");
            }

            return null;
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