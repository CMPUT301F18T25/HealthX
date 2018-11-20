/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.graphics.Bitmap;

import java.util.Date;

public class Record {
    protected String title;
    protected String comment;
    protected Double longitude;
    protected Double latitude;
    protected Bitmap image;
    protected String date;
    protected String id;


    public Record(String record_title,String record_comment, Double latitude, Double longitude, Bitmap image, String date){
        this.title = record_title;
        this.comment = record_comment;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.date = date;
        this.id = "";

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

    public void setImage(Bitmap image){
        this.image = image;
    }

    public Bitmap getImage(){
        return this.image;
    }

    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }


}

