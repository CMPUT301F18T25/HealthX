/*
 *  * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.graphics.Bitmap;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;

public class Record  implements Serializable {

    protected String title;
    protected String comment;
    protected Double longitude;
    protected Double latitude;
    protected Bitmap image;
    protected String date;
    protected String id;
    protected String problemID; // problemId specifies the feild at which the record is associated with a problem


    public Record(String record_title,String record_comment, Double latitude, Double longitude, Bitmap image, String date, String problemID){
        /**
         * Creates an instance of Record with getter and setters for the parameters
         *
         * @param record_title the title the user entered for the record
         * @param record_comment the comment the user entered for the record
         * @param latitude the latitude from geolocation of user
         * @param longitude the longitude from geolocation of user
         * @param image the bitmap of the photo taken by the user for a record
         * @param date the date created the user selected for the record
         *
         */
        this.title = record_title;
        this.comment = record_comment;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.date = date;
        this.id = "";
        this.problemID = problemID;

    }
    /**
     * sets Comment
     * @param comment comment to be added*/
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * sets Title
     * @param title title to be added*/
    public void setTitle(String title){
        this.title = title;
    }
    /**
     * returns Comment
     * */
    public String getComment() {
        return comment;
    }
    /**
     * returns Title
     * */
    public String getTitle() {
        return title;
    }
    /**
     * sets Date
     * @param setDate date to set
     * */
    public void setDate(String newDate){
        this.date = newDate;
    }
    /**
     * returns date
     * */
    public String getDate(){
        return this.date;
    }
    /**
     * returns Latitude
     * */
    public Double getLatitude() {
        return latitude;
    }
    /**
     * returns Longitude
     * */
    public Double getLongitude() {
        return longitude;
    }
    /**
     * sets Latitude
     * @param latitude latitude you want to set
     * */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    /**
     * sets Longitude
     * @param longitude longitude you want to set
     * */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    /**
     * sets image
     * @param image image you want to set
     * */
    public void setImage(Bitmap image){
        this.image = image;
    }
    /**
     * returns image
     * */
    public Bitmap getImage(){
        return this.image;
    }
    /**
     * gets Id
     * */
    public String getId(){
        return this.id;
    }
    /**
     * sets Id
     * @param Id Id you want to set
     * */
    public void setId(String id){
        this.id = id;
    }

    public void setProblemID(String problemID){
        this.problemID = problemID;
    }
    public String getProblemID(){
        return this.problemID;
    }

}