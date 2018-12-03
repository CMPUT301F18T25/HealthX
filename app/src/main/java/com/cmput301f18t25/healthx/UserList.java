/*
 * Class Name: UserList
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */


package com.cmput301f18t25.healthx;

import java.util.ArrayList;

/**
 * This is the userList singleton for offline login
 *
 * @author Dhrub
 * @version 1.0
 *
 */

// singleton for offline
public class UserList {
    private static  UserList instance;
    private static ArrayList<User> userlist;
    public static User previousUser;


    public static UserList getInstance() {
        if (instance == null) {
            instance = new UserList();
        }
        return instance;
    }


    private UserList() {
        userlist = new ArrayList<>();
    }

    public void SetUserList(ArrayList<User> array) {
        userlist = array;
    }

    public ArrayList<User> getUserlist() {
        return userlist;
    }

    public void addToList(User u){
        userlist.add(u);

    }

    public User getUserByUsername(String username) {

        for (User u: userlist) {
            if (u.getUsername().compareTo(username) == 0) {
                return u;
            }
        }
        return  null;
    }

    public void setPreviousUser(User u) {
        previousUser = u;
    }

    public User getPreviousUser() {

        if (previousUser == null) {
            return null;
        }
        else {
            return previousUser;
        }
    }
//
//    public User getLastUser() {
//        return userlist.get(userlist.size()-1);
//    }



}
