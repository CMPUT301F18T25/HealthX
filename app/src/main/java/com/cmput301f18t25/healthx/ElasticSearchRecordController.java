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
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class ElasticSearchRecordController {
    private static JestDroidClient client;

    // adds record to elasticsearch
    public static class AddRecordTask extends AsyncTask<Record, Void, Void> {

        @Override
        protected Void doInBackground(Record... records) {
            clientSet();
            for (Record record : records) {
                Index index = new Index.Builder(record).index("cmput301f18t25test").type("record").build();

                try {
                    DocumentResult result1 = client.execute(index);
                    if (!result1.isSucceeded()) {
                        Log.d("ElasticRecord", "Elastic search was not able to add record.");
                    }else {
                        Log.d("ElasticRecord", "Elastic search added record");
                    }

                }
                catch (Exception e) {
                    Log.d("ElasticRecord", "The application failed to build and send the record");
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
            String query = "{\n" + "\"from\" : 0, \"size\": 100,\n" +
                    "    \"query\": {\n" +
                    "                \"match_all\" : {}\n"  +  " }\n}\n";


            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t25test")
                    .addType("record")
                    .build();
            try {
                JestResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Record> recordList;
                    recordList = result.getSourceAsObjectList(Record.class);
                    records.addAll(recordList);
                }

            } catch (IOException e) {
                Log.d("Error", "Error in searching records");
            }

            return records;
        }

    }
    public static class DeleteRecordTask extends AsyncTask<Record, Void, Void> {

        @Override
        protected Void doInBackground(Record... records) {
            clientSet();

            String query = "{\n" +
                    "    \"query\": {\n" +
                    "                \"bool\" : {\n" +
                    "\"should\" : [\n"+ "{\"match\" : {\"_id\" : \""+ records[0]+ "\"}},\n" +"\"}}\n]\n}\n}\n}\n";

            Delete delete = new Delete.Builder(query).index("cmput301f18t25test").type("record").build();

            try {
                DocumentResult result1 = client.execute(delete);
                if (!result1.isSucceeded()) {
                    Log.d("ElasticRecord", "Elastic search was not able to delete record.");
                }else {
                    Log.d("ElasticRecord", "Elastic search deleted record");
                }

            }
            catch (Exception e) {
                Log.d("ElasticProblem", "The application failed to build and send the record");
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