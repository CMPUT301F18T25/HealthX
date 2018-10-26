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
    protected Date date;
    protected ArrayList<Record> recordArray = new ArrayList<Record>();

//    Was in the story board
    protected Integer count;

    public Problem(String problemTitle, String problemDescription, Date problemDate){
        this.title = problemTitle;
        this.description = problemDescription;
        this.date = problemDate;
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

    public void setDate(Date newDate){
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

}
