package com.cmput301f18t25.healthx;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class ElasticSearchProblemController {
    private static JestDroidClient client;

    // adds problem to elasticsearch
    public static class AddProblemTask extends AsyncTask<Problem, Void, Void> {

        @Override
        protected Void doInBackground(Problem... problems) {
            setClient();
            String problemID;
            for (Problem problem : problems){
                Index index = new Index.Builder(problem).index("cmput301f18t25test").type("problems").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    if (!result1.isSucceeded()) {
                        Log.i("Error", "Elasticsearch was not able to add problem.");
                    } else {
                        problemID = result1.getId();
                        problem.setId(problemID);
                        Index index1 = new Index.Builder(problem).index("cmput301f18t25test").type("newProblem").build();
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

    public static class GetProblemsTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... params) {
            setClient();
            ArrayList<Problem> problems = new ArrayList<Problem>();

            Search search = new Search.Builder(params[0])
                    .addIndex("cmput301f18t25test")
                    .addType("newProblem")
                    .build();
            try {
                JestResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Problem> problemList;
                    problemList = result.getSourceAsObjectList(Problem.class);
                    problems.addAll(problemList);
                }

            } catch (IOException e) {
                Log.d("Error", "Error in searching problems");
            }

            return problems;
        }

    }

    public static class SearchProblemsTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... params) {
            setClient();
            ArrayList<Problem> problems = new ArrayList<Problem>();
            String keyword = params[0];
            if (params[1] != null && params[2] != null){
                Integer latitude = Integer.valueOf(params[1]);
                Integer longitude = Integer.valueOf(params[2]);
                String query = "{\"query\" : { \"bool\" : { \"must\" : [ { \"range\" : { \"latitude\" : { \"gte\" : \"" + latitude + "\", \"lte\" : \"" + latitude + "\" } } }, { \"range\" : { \"longitude\" : { \"gte\" : \"" + longitude + "\", \"lte\" : \"" + longitude + "\" }}}]}}}";
            }

            String query = "{\"query\" : { \"query_string\" : { \"query\" : \"" + "*" + keyword + "*" + "\", \"fields\" : [\"title\" , \"description\"]}}}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")
                    .addType("newProblem")
                    .build();
            try {
                JestResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Problem> problemList;
                    problemList = result.getSourceAsObjectList(Problem.class);
                    problems.addAll(problemList);
                }

            } catch (IOException e) {
                Log.d("Error", "Error in searching problems");
            }

            return problems;
        }

    }

    public static class DeleteProblemTask extends AsyncTask<Problem, Void, Void> {

        @Override
        protected Void doInBackground(Problem... problems) {
            setClient();
            String query = "{\"query\" : { \"match\" : { \"id\" : \"" + problems[0].getId() + "\"}}}";
            DeleteByQuery delete = new DeleteByQuery.Builder(query).addIndex("cmput301f18t25test").addType("newProblem").build();
            try {
                client.execute(delete);
            } catch (Exception e) {
                Log.d("ElasticProblem", "The application failed to build and send the problem");
            }

            return null;
        }

    }

    public static void setClient() {
        if (client == null) {

            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}