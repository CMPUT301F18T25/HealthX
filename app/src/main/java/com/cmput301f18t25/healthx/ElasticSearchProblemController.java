
package com.cmput301f18t25.healthx;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

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
                        Index index1 = new Index.Builder(problem).index("cmput301f18t25test").type("newProblem2").build();
                        try {
                            DocumentResult result2 = client.execute(index1);

                            if (!result2.isSucceeded()) {
                                Log.i("Error", "doInBackground: error");
                            }
                            else{
                                Log.d("CWei","problem added");
                                Log.d("CWei",problem.getUserId());
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


    public static class UpdateProblemTask extends AsyncTask<Problem, Void, Void> {

        @Override
        protected Void doInBackground(Problem... problems) {
            setClient();
            for (Problem problem : problems){
                Index index = new Index.Builder(problem).index("cmput301f18t25test").type("newProblem2").build();

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


    public static class GetProblemsTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... params) {
            setClient();
            ArrayList<Problem> problems = new ArrayList<Problem>();

            String query = "{ \"query\" : { \"match\" :  { \"userId\" : \""+ params[0] + "\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")
                    .addType("newProblem2")
                    .build();
            try {
//                JestResult result = client.execute(search);
                JestResult result = client.execute(search);
                if (result.isSucceeded()) {
                    Log.d("IVANLIM", "doInBackground: succeded :)");
                    List<Problem> problemList;
                    problemList = result.getSourceAsObjectList(Problem.class);
                    problems.addAll(problemList);
                   // Collections.sort(problems,Problem.RecDateComparator);
                    Log.d("CWei", String.valueOf(problemList.size()));
                    Log.d("IVANLIM", String.valueOf(problemList.size()));
                }
                else {
                    Log.d("IVANLIM", "Else caluse: ");
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
            String query;
            if (params.length == 2){
                query = "{\"query\" : { \"bool\" : { \"must\": [{ \"query_string\" : { \"query\" : \"" + "*" + params[1] + "*" + "\", \"fields\" : [\"frontBodyLocation\" , \"backBodyLocation\"]} },{ \"query_string\" : { \"query\" : \"" + "*" + keyword + "*" + "\" , \"fields\" : [\"title\" , \"description\"]} }] } }}";
            }else {
                query = "{\"query\" : { \"query_string\" : { \"query\" : \"" + "*" + keyword + "*" + "\", \"fields\" : [\"title\" , \"description\"]}}}";
            }
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")
                    .addType("newProblem2")
                    .build();
            try {
//                JestResult result = client.execute(search);
                JestResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Problem> problemList;
                    problemList = result.getSourceAsObjectList(Problem.class);
                    problems.addAll(problemList);
                }
                else {
                    Log.d("IVANLIM", "Else caluse: ");
                }

            } catch (IOException e) {
                Log.d("Error", "Error in searching problems");
            }

            return problems;
        }

    }

    public static class SearchProblemsFromRecordsTask extends AsyncTask<ArrayList<Record>, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(ArrayList<Record>... params) {
            setClient();
            ArrayList<Problem> problems = new ArrayList<Problem>();
            ArrayList<Record> records = params[0];
            for (Record record : records){
                String problemId = record.getProblemID();
                String query = "{ \"query\" : { \"match\" :  { \"id\" : \""+ problemId + "\"}}}";
                Search search = new Search.Builder(query)
                        .addIndex("cmput301f18t25test")
                        .addType("newProblem2")
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


            }

            return problems;
        }
    }

    public static class DeleteProblemTask extends AsyncTask<Problem, Void, Void> {

        @Override
        protected Void doInBackground(Problem... problems) {
            Log.d("IVANLIM", "delete ");
            setClient();
            String query = "{\"query\" : { \"match\" : { \"id\" : \"" + problems[0].getId() + "\"}}}";
            DeleteByQuery delete = new DeleteByQuery.Builder(query).addIndex("cmput301f18t25test").addType("newProblem2").build();
            try {
                Log.d("IVANLIM", "delete ");
                client.execute(delete);
            } catch (Exception e) {
                Log.d("IVANLIM", "The application failed to build and send the problem in delete");
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
