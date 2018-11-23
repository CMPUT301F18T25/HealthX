/*
 *  * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

public class User {

    protected String name;
    protected String username;
    protected String phoneNumber;
    protected String email;
    protected String id;
    //    status meaning if the user is a patient or a care provider.
    protected String status;

    protected String reminderFrequency;

    /**
     * Creates an instance of User with getter and setters for the parameters
     *
     * @param name the name of the user
     * @param user_name the username the user inputted in signUp
     * @param user_phoneNumber the phone number of the user
     * @param user_email the email address of the user
     * @param user_status the status of the user is either a patient or care provider
     */
    public User(String name, String user_name, String user_phoneNumber, String user_email, String user_status){
        this.name = name;
        this.username = user_name;
        this.phoneNumber = user_phoneNumber;
        this.email = user_email;
        this.status = user_status;
        this.id = "";
    }
    /**
     * Clones user, is required for elastic search
     * @param user user to be cloned
     * */
    public void cloneUser(User user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.id = user.getId();
    }

    public void setName(String text){
        this.name = text;
    }

    public String getName(){
        return this.name;
    }

    public void setUsername(String text){
        this.username = text;
    }

    public String getUsername(){
        return this.username;
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

    public String getStatus() {
        return status;
    }


    public String getReminderFrequency(){
        return this.reminderFrequency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
