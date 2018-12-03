/*
 *  * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Problem implements Serializable{
    protected String title;
    protected String description;
    protected String date;
    protected ArrayList<Record> recordArray;
    protected Integer count;
    protected String id;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    protected String userId; // not the username

    public Problem(String problemTitle, String problemDescription, String problemDate, String userId){

        /**
         * Creates an instance of Problem with getter and setters for the parameters
         *
         * @param problemTitle the title the user entered for the problem
         * @param problemDescription the description user entered for the problem
         * @param problemDate the date created that the user selected for the problem
         *
         */

        this.title = problemTitle;
        this.description = problemDescription;
        this.date = problemDate;
        this.id = "";
        this.userId = userId;
        this.recordArray = new ArrayList<Record>();
    }

    public void setTitle(String text){
        this.title = text;
    }

    public String getTitle(){
        return this.title;
    }

    public void setDescription(String text){
        this.description = text;
    }

    public String getDescription(){
        return  this.description;
    }

    public void setDate(String newDate){
        this.date = newDate;
    }

    public String getDate(){
        return this.date;
    }

    public ArrayList<Record> getRecordArray() {
        return this.recordArray;
    }

    /**
     * adds Record
     * @param item Record to be added
     * */
    public void addRecord(Record item){
        recordArray.add(item);
    }
    /**
     * deletes Record given
     * @param item Record to be deleted
     * */
    public void deleteRecord(Record item){
        for (int i=0; i<recordArray.size();i++){
            Record object = recordArray.get(i);
            if (object.getDate().equals(item.getDate())){
                recordArray.remove(i);
            }
        }
    }
    public static Comparator<Problem> RecDateComparator = new Comparator<Problem>() {

        public int compare(Problem problem1, Problem problem2) {
            String Date1 = problem1.getDate();
            String Date2 = problem2.getDate();

            //ascending order
            return Date1.compareTo(Date2);
        }};

    public Integer getCount() {
        return recordArray.size();
    }
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }
}
