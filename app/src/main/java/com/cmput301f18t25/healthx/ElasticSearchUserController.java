/*
 * Class Name: ElasticSearchUserController
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
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
 * This is the elasticSearch controller for problem
 *
 *
 * @author Ivan
 * @author Dhruba
 * @author Cecilia
 * @version 1.0
 *
 */

public class ElasticSearchUserController {
    private static JestDroidClient client;

    /**
     *
     * add the user to the user table
     *
     */
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
    /**
     *
     * get a user from the user table
     *
     */

    public static class GetUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... users) {
//            "{n\"query\" : {\"term\" : { \"userID\" : \"hai\" }}}";
            verifySettings();
            User theUser = new User("", "", "", "", "", "");
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
//
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

    /**
     *
     * add a request code associated with the user
     *
     */

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

    /**
     *
     * delete a request code.
     *
     */

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

    /**
     *
     * get the request code by user name
     *
     */
    public static class GetRequestCodeTask extends AsyncTask<String, Void, ArrayList<RequestCode>> {
        @Override
        protected ArrayList<RequestCode> doInBackground(String... params) {
            verifySettings();
            ArrayList<RequestCode> requestCodes = new ArrayList<RequestCode>();
            String query = "{ \"query\" : { \"match\" :  { \"user_code\" : \"" + params[0] + "\"}}}";
            Search search = new Search.Builder(query)

                    .addIndex("cmput301f18t25test")
                    .addType("newReqCodes")
                    .build();

            try {
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

    /**
     *
     * update user profile
     *
     */

    public static class UpdateUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();
            for (User user : users){
                Index index = new Index.Builder(user).index("cmput301f18t25test").type("usernew2").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    if (!result1.isSucceeded()) {
                        Log.i("Error", "Elasticsearch was not able to add problem.");
                    }
                }
                catch (Exception e){
                    Log.i("Error", "The application failed to build and send the tweets");
                }
            }
            return null;

        }

    }

    /**
     *
     * delete a user account
     *
     */

    public static class DeleteUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();
            String query = "{\"query\" : { \"match\" : { \"id\" : \"" + users[0].getId() + "\"}}}";
            DeleteByQuery delete = new DeleteByQuery.Builder(query).addIndex("cmput301f18t25test").addType("usernew2").build();
            try {
                client.execute(delete);
            } catch (Exception e) {
                Log.d("ElasticProblem", "The application failed to build and send the problem");
            }

            return null;
        }

    }

    /**
     *
     * before adding the patient to the doctor's list, we validate if the patient is assigned to a doctor already.
     *
     */
    public static class CheckPatientTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... users) {
//            "{n\"query\" : {\"term\" : { \"userID\" : \"hai\" }}}";
            verifySettings();
            User theUser = new User("", "", "", "", "","");
            String query ="{ \"query\" : { \"match\" :  { \"username\" : \""+ users[0] + "\"}}}";
             //Build the query

            String userId = null;
            ArrayList<User> userArray = new ArrayList<User>();
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")
                    .addType("myPatient")
                    .build();

            try {
                // gets result
                SearchResult result = client.execute(search);
//                DocumentResult docres = client.execute(search);
//                SearchRes
                if (result.isSucceeded()) {
//
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

    /**
     *
     * check patient by request code
     *
     */
    public static class CheckPatientTaskRequestCode extends AsyncTask<String, Void, ArrayList<RequestCode>> {
        @Override
        protected ArrayList<RequestCode> doInBackground(String... users) {
//            "{n\"query\" : {\"term\" : { \"userID\" : \"hai\" }}}";
            ArrayList<RequestCode> requestCodes = new ArrayList<RequestCode>();

            String query = "{ \"query\" : { \"match\" :  { \"name\" : \""+ users[0] + "\"}}}";
            //Build the query

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")
                    .addType("myPatient")
                    .build();

            try {
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

    /**
     *
     * get the care provider's patient list
     *
     */

    public static class GetPatientsTask extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... params) {

            verifySettings();
            ArrayList<User> patients = new ArrayList<User>();
            String query = "{\n" + "\"from\" : 0, \"size\": 100,\n" +
                    "    \"query\": {\n" +
                    "                \"match\" : {\"doctorID\": \""+ params[0] + "\" }\n"  +  " }\n}\n";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")

                    .addType("myPatient")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    Log.d("CeciliaW", "doInBackground: patient SUCESS");
                    List<User> patientList;
                    patientList = result.getSourceAsObjectList(User.class);
                    patients.addAll(patientList);
                    Log.d("CWei", String.valueOf(patients.size()));
                    Log.d("CWei", "prted");

                }
                else {
                    Log.d("CeciliaW", "doInBackground: patient ELSe");
                }

            } catch (IOException e) {
                Log.d("Error", "Error in searching records");
            }

            return patients;
        }

    }

    /**
     *
     * assign a patient to the doctor
     *
     */
    public static class AddPatientTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... patients) {
            verifySettings();
            String patientID;
            for (User patient : patients){
                Index index = new Index.Builder(patient).index("cmput301f18t25test").type("patients").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    if (!result1.isSucceeded()) {
                        Log.i("Error", "Elasticsearch was not able to add problem.");
                    } else {
                        //patientID = result1.getId();
                        //patient.setId(patientID);
                        Index index1 = new Index.Builder(patient).index("cmput301f18t25test").type("myPatient").build();
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
                catch (Exception e){
                    Log.i("Error", "The application failed to build and send the tweets");
                }
            }
            return null;

        }
    }

    /**
     *
     * add patient by request code
     *
     */
    public static class AddPatientRequestCodeTask extends AsyncTask<RequestCode, Void, Void> {

        @Override
        protected Void doInBackground(RequestCode... patients) {
            verifySettings();
            String patientID;
            for (RequestCode rc : patients){
                Index index = new Index.Builder(rc).index("cmput301f18t25test").type("patients").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    Log.i("here","the fuck??");
                    if (!result1.isSucceeded()) {
                        Log.i("Error", "Elasticsearch was not able to add patient.");
                    } else {
                        //patientID = result1.getId();
                        //patient.setId(patientID);
                        Index index1 = new Index.Builder(rc).index("cmput301f18t25test").type("myPatientCodes").build();
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
                catch (Exception e){
                    Log.i("Error", "The application failed to build and send the tweets");
                }
            }
            return null;

        }
    }

    /**
     *
     * delete a patient
     *
     */
    public static class DeletePatientTask extends AsyncTask<User, Void, Void> {
        // TODO: shall we get rid of email for this query too?

        @Override
        protected Void doInBackground(User... patients) {
            verifySettings();
            //String query = "{\"query\" : { \"match\" : { \"user\" : \"" + records[0].getId() + "\"}}}";
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "                \"bool\" : {\n" +
                    "\"must\" : [\n"+ "{\"match\" : {\"username\" : \""+ patients[0].getUsername()+ "\"}},\n" + "{\"match\" : {\"email\" : \""+ patients[0].getEmail()+"\"}}\n]\n}\n}\n}\n";
            DeleteByQuery delete = new DeleteByQuery.Builder(query).addIndex("cmput301f18t25test").addType("myPatient").build();
            try {
                client.execute(delete);
            } catch (Exception e) {
                Log.d("ElasticProblem", "The application failed to build and send the problem");
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