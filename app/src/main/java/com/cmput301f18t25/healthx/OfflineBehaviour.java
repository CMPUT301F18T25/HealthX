package com.cmput301f18t25.healthx;


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

    private LinkedList<Object> objectQueue;
    private LinkedList<String> objectAction;
    // object can be either probelm or record

    public OfflineBehaviour() {
        objectQueue = new LinkedList<>();
        objectAction = new LinkedList<>();
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

        for (int index= 0; index < objectQueue.size(); index++) {
            Object obj = objectQueue.get(index);
            String action = objectAction.get(index);

            if (action.compareTo("ADD") == 0 && obj.getClass() == Problem.class) {
                // call elasticsearch for problemAdd
                ElasticSearchProblemController.AddProblemTask addproblem = new ElasticSearchProblemController.AddProblemTask();
                addproblem.execute((Problem) obj);

            }
            else if (action.compareTo("ADD") == 0 && obj.getClass() == Record.class) {
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
