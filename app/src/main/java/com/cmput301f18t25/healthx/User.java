/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

public class User {

    protected String name;
    protected String userId;
    protected String phoneNumber;
    protected String email;

    //    status meaning if the user is a patient or a care provider.
    protected String status;

    protected String reminderFrequency;

    public User(String user_name, String id, String user_phoneNumber, String user_email, String user_status){
        this.name = user_name;
        this.userId = id;
        this.phoneNumber = user_phoneNumber;
        this.email = user_email;
        this.status = user_status;
    }

    public void setName(String text){
        this.name = text;
    }

    public String getName(){
        return this.name;
    }

    public void setUserId(String text){
        this.userId = text;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setPhoneNumber(String text){
        this.phoneNumber = text;
    }

    public String getPhoneNumber(){
        return  this.phoneNumber;
    }

    public void setEmail(String text){
        this.email = text;
    }
    public String getEmail(){
        return this.email;
    }

    public void setReminderFrequency(String text){
        this.reminderFrequency = text;
    }

    public String getReminderFrequency(){
        return this.reminderFrequency;
    }
}
