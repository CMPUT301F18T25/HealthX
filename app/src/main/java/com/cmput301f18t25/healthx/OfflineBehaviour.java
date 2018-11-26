package com.cmput301f18t25.healthx;


import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * USE CASE: As a patient, I want to add or edit problems and records while off the network,
 * and have these changes synchronized once I regain connectivity.
 */

public class OfflineBehaviour {

    private static OfflineBehaviour instance;
    private LinkedList<Object> objectQueue = new LinkedList<>();
    private LinkedList<String> objectAction = new LinkedList<>();
    // object can be either probelm or record

    public static OfflineBehaviour getInstance() {
        if (instance == null) {
            instance = new OfflineBehaviour();
        }
        return instance;
    }

    OfflineBehaviour() {
    }

    public Queue<String> getActionQueue() {
        return objectAction;
    }

    public Queue<Object> getObjectQueue() {
        return objectQueue;
    }


    public void addItem(Object o, String s) {
        objectQueue.add(o);
        objectAction.add(s);
    }

    public void removeAtIndex(int i) {
        objectQueue.remove(i);
        objectAction.remove(i);
    }

    public void synchronizeWithElasticSearch() {
        Log.d("synchRecord", "synchronizeWithElasticSearch: begin" + String.valueOf(objectQueue.size()) + " "+ String.valueOf(objectAction.size()));
        for (int index= 0; index < objectQueue.size(); index++) {
            Object obj = objectQueue.get(index);
            String action = objectAction.get(index);
            Log.d("synchRecord", "synchronizeWithElasticSearch: " + action);

            if (action.compareTo("ADD") == 0 && obj.getClass() == Problem.class) {
                // call elasticsearch for problemAdd
                ElasticSearchProblemController.AddProblemTask addproblem = new ElasticSearchProblemController.AddProblemTask();
                addproblem.execute((Problem) obj);

            }
            else if (action.compareTo("ADD") == 0 && obj.getClass() == Record.class) {
                Log.d("synchRecord", "synchronizeWithElasticSearch: " + obj.getClass());
                ElasticSearchRecordController.AddRecordTask addrecord = new ElasticSearchRecordController.AddRecordTask();
                addrecord.execute((Record) obj);

            }
            else if (action.compareTo("DELETE") == 0 && obj.getClass() == Problem.class) {
                ElasticSearchProblemController.DeleteProblemTask deleteProblemTask = new ElasticSearchProblemController.DeleteProblemTask();
                deleteProblemTask.execute((Problem) obj);

            }
            else if (action.compareTo("DELETE") == 0 && obj.getClass() == Record.class) {
                ElasticSearchRecordController.DeleteRecordTask deleteRecordTask = new ElasticSearchRecordController.DeleteRecordTask();
                deleteRecordTask.execute((Record) obj);

            }
//            else if (action.compareTo("EDIT") == 0 && obj.getClass() == Problem.class) {
//
//            }
//            else if (action.compareTo("EDIT") == 0 && obj.getClass() == Record.class) {
//
//            }
        }

        objectQueue.clear();
        objectAction.clear();
    }






}
