package com.cmput301f18t25.healthx;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ElasticSearchRecordController {
    private static JestDroidClient client;

    // adds record to elasticsearch
    public static class AddRecordTask extends AsyncTask<Record, Void, Void> {

        @Override
        protected Void doInBackground(Record... records) {
            clientSet();
            String recordID;
            for (Record record : records){
                Index index = new Index.Builder(record).index("cmput301f18t25test").type("records").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    if (!result1.isSucceeded()) {
                        Log.i("Error", "Elasticsearch was not able to add problem.");
                    } else {
                        recordID = result1.getId();
                        record.setId(recordID);
                        Index index1 = new Index.Builder(record).index("cmput301f18t25test").type("newRecord3").build();
                        try {
                            DocumentResult result2 = client.execute(index1);
                            if (!result2.isSucceeded()) {
                                Log.i("Error", "doInBackground: error");
                            }else {
                                Log.d("CWei", "doInBackground: successed addrecord ");
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
    public static class GetRecordsTask extends AsyncTask<String, Void, ArrayList<Record>> {
        @Override
        protected ArrayList<Record> doInBackground(String... params) {

            clientSet();
            ArrayList<Record> records = new ArrayList<Record>();

            // SHOULD CHANGE TO DYNAMIC SIZE
            String query = "{\n" + "\"from\" : 0, \"size\": 100,\n" +
                    "    \"query\": {\n" +
                    "                \"match\" : {\"problemID\": \""+ params[0] + "\" }\n"  +  " }\n}\n";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")

                    .addType("newRecord3")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    Log.d("IVANLIM", "doInBackground: RECORD SUCESS");
                    List<Record> recordList;
                    recordList = result.getSourceAsObjectList(Record.class);
                    records.addAll(recordList);
                    Log.d("CWei", String.valueOf(recordList.size()));

                }
                else {
                    Log.d("IVANLIM", "doInBackground: RECORD ELSe");
                }

            } catch (IOException e) {
                Log.d("Error", "Error in searching records");
            }

            return records;
        }

    }

    public static class SearchRecordsTask extends AsyncTask<String, Void, ArrayList<Record>> {
        @Override
        protected ArrayList<Record> doInBackground(String... params) {
            clientSet();
            ArrayList<Record> records = new ArrayList<Record>();
            String keyword = params[0];
            String query;
            if (params.length == 3){

                // APPROXIMATELY WITHIN A 5 KM RANGE

                Double longitudeRange = (2.5 / (111.320 * Math.toDegrees(Math.cos(Math.toRadians(Double.valueOf(params[1]))))));

                Log.d("UWU" , longitudeRange.toString());
                Double minLatitude = Double.valueOf(params[1]) - 0.025;
                Double minLongitude = Double.valueOf(params[2]) - longitudeRange;
                Double maxLatitude = Double.valueOf(params[1]) + 0.025;
                Double maxLongitude = Double.valueOf(params[2]) + longitudeRange;

                query = "{\"query\" : { \"bool\" : { \"must\": [ { \"range\": { \"latitude\" : { \"gte\" : " + minLatitude + ", \"lte\" : " + maxLatitude + " } } },{ \"range\": { \"longitude\": { \"gte\" : " + minLongitude + ", \"lte\": " + maxLongitude + " } } },{ \"query_string\" : { \"query\" : \"" + "*" + keyword + "*" +  "\", \"fields\" : [\"title\" , \"comment\"]} }]} }}";

            } else {

                query = "{\"query\" : { \"query_string\" : { \"query\" : \"" + "*" + keyword + "*" + "\", \"fields\" : [\"title\" , \"comment\"]}}}";

            }

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")
                    .addType("newRecord3")
                    .build();
            try {
                JestResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Record> recordList;
                    recordList = result.getSourceAsObjectList(Record.class);
                    records.addAll(recordList);
                }

            } catch (IOException e) {
                Log.d("Error", "Error in searching problems");
            }

            return records;
        }

    }

    public static class DeleteRecordTask extends AsyncTask<Record, Void, Void> {

        @Override
        protected Void doInBackground(Record... records) {
            clientSet();
            String query = "{\"query\" : { \"match\" : { \"id\" : \"" + records[0].getId() + "\"}}}";
            DeleteByQuery delete = new DeleteByQuery.Builder(query).addIndex("cmput301f18t25test").addType("newRecord3").build();
            try {
                client.execute(delete);
            } catch (Exception e) {
                Log.d("ElasticProblem", "The application failed to build and send the problem");
            }

            return null;
        }

    }
    public static void clientSet() {
        if (client == null) {

            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}