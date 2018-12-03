package com.cmput301f18t25.healthx;

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
