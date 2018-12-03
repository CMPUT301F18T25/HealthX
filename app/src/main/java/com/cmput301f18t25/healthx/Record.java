/*
 * Class Name: Record
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;

import android.graphics.Bitmap;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
public class Record  implements Serializable {

    protected String title;
    protected String comment;
    protected Double longitude;
    protected Double latitude;
    protected ArrayList<String> imageURIs;
    protected String date;
    protected String id;
    protected String problemID; // problemId specifies the feild at which the record is associated with a problem

    public boolean isCPComment() {
        return CPComment;
    }

    public void setCPComment(boolean CPComment) {
        this.CPComment = CPComment;
    }
    protected boolean CPComment;

    public Record(String record_title, String record_comment, Double latitude, Double longitude, ArrayList<String> imageURIs, String date, String problemID){
    /**
     * Creates an instance of Record with getter and setters for the parameters
     *
     * @param record_title the title the user entered for the record
     * @param record_comment the comment the user entered for the record
     * @param latitude the latitude from geolocation of user
     * @param longitude the longitude from geolocation of user
     * @param image the bitmap of the photo taken by the user for a record
     * @param date the date created the user selected for the record
     * @param problemID the problem id to link the problem with the record
     */

        this.title = record_title;
        this.comment = record_comment;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageURIs = imageURIs;
        this.date = date;
        this.id = "";
        this.problemID = problemID;

    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getComment() {
        return comment;
    }
    public String getTitle() {
        return title;
    }
    public void setDate(String newDate){
        this.date = newDate;
    }
    public String getDate(){
        return this.date;
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public void setImageURIs(ArrayList<String> imageURIs){
        this.imageURIs = imageURIs;
    }
    public ArrayList<String> getImageURIs(){
        return this.imageURIs;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }

    public void setProblemID(String problemID){
        this.problemID = problemID;
    }
    public String getProblemID(){
        return this.problemID;
    }
    public static Comparator<Record> RecDateComparator = new Comparator<Record>() {

        public int compare(Record record1, Record record2) {
            String Date1 = record1.getDate();
            String Date2 = record2.getDate();

            //ascending order
            return Date1.compareTo(Date2);
        }};
}