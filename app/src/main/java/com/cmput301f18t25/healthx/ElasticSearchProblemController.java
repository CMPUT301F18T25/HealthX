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
            for (Problem problem : problems) {
                Index index = new Index.Builder(problem).index("cmput301f18t25test").type("problem").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    if (!result1.isSucceeded()) {
                        Log.d("ElasticProblem", "Elastic search was not able to add problem.");
                    }else {
                        Log.d("ElasticProblem", "Elastic search added problem");
                    }

                }
                catch (Exception e) {
                    Log.d("ElasticProblem", "The application failed to build and send the problem");
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
                    .addType("problem")
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

            String query = "{\"query\" : { \"bool\" : { \"must\" : { \"match\" : { \"title\" : \"" + "Sup" + "\"}}}}}";

            Delete delete = new Delete.Builder(problems[0].getTitle()).index("cmput301f18t25test").type("problem").build();

            try {
                DocumentResult result1 = client.execute(delete);
                if (!result1.isSucceeded()) {
                    Log.d("ElasticProblem", "Elastic search was not able to delete problem.");
                }else {
                    Log.d("ElasticProblem", "Elastic search deleted problem");
                }
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