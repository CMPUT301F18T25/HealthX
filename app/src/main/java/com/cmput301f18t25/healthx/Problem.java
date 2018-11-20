/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import java.util.ArrayList;
import java.util.Date;

public class Problem {
    protected String title;
    protected String description;
    protected String date;
    protected ArrayList<Record> recordArray = new ArrayList<Record>();
    protected Integer count;
    protected String id;

    public Problem(String problemTitle, String problemDescription, String problemDate){
        this.title = problemTitle;
        this.description = problemDescription;
        this.date = problemDate;
        this.id = "";
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

    public void addRecord(Record item){
        recordArray.add(item);
    }

    public void deleteRecord(Record item){
        for (int i=0; i<recordArray.size();i++){
            Record object = recordArray.get(i);
            if (object.getDate().equals(item.getDate())){
                recordArray.remove(i);
            }
        }
    }

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
