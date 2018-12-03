/*
 * Class Name: RequestCod
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

/**
 * This is the entity class for the request code object.
 *
 * @author Ajay
 * @version 1.0
 *
 */

public class RequestCode {

    protected String username;
    protected String user_code;
    private String doctorID;

    /**
     * Creates an instance of User with getter and setters for the parameters
     *
     * @param user_name the username the user inputted in signUp
     * @param user_code the code the user is assigned to in signUp
     */
    public RequestCode(String user_name, String user_code){
        this.username = user_name;
        this.user_code = user_code;

    }


    public void setUsername(String text){
        this.username = text;
    }

    public String getUsername(){
        return this.username;
    }

    public String getCode() {
        return this.user_code;
    }

    public void setCode(String code) {
        this.user_code = code;
    }


    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }


}
