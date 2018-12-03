/*
 * Class Name: UserTest
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is the unit test for user class
 *
 * @author Aida
 * @version 1.0
 *
 */

public class UserTest {

    public String test_name = "name";
    public String test_username = "usrname";
    public String test_phone_number = "1234567890";
    public String test_email = "patient@email.com";
    public String test_status = "patient";
    public String test_freq = "daily";

    public UserTest(){}

    @Test
    public void testCloneUser(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);

        assertEquals(user.getName(),test_name);
        assertEquals(user.getUsername(),test_username);
        assertEquals(user.getPhoneNumber(),test_phone_number);
        assertEquals(user.getEmail(),test_email);
        assertEquals(user.getStatus(),test_status);
        assertEquals(user.getReminderFrequency(),test_freq);

        User user2 = new User("name2","usrname2","0987654321","doctor@email.com","care provider","weekly");

        user.cloneUser(user2);

        assertEquals(user.getName(),"name2");
        assertEquals(user.getUsername(),"usrname2");
        assertEquals(user.getPhoneNumber(),"0987654321");
        assertEquals(user.getEmail(),"doctor@email.com");
        assertEquals(user.getStatus(),"care provider");
        assertEquals(user.getReminderFrequency(),"weekly");

    }

    @Test
    public void testGetName(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        assertEquals(user.getName(),test_name);
    }

    @Test
    public void testSetName(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        String new_name = "new_name";
        user.setName(new_name);
        assertEquals(user.getName(),new_name);
    }

    @Test
    public void testGetUsername(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        assertEquals(user.getUsername(),test_username);

    }

    @Test
    public void testSetUsername(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        String new_username = "new_usrnm";
        user.setUsername(new_username);
        assertEquals(user.getUsername(),new_username);
    }

    @Test
    public void testGetPhoneNumber(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        assertEquals(user.getPhoneNumber(),test_phone_number);

    }

    @Test
    public void testSetPhoneNumber(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        String new_number = "7564536123";
        user.setPhoneNumber(new_number);
        assertEquals(user.getPhoneNumber(),new_number);
    }

    @Test
    public void testGetEmail(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        assertEquals(user.getEmail(),test_email);

    }

    @Test
    public void testSetEmail(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        String new_email = "new_email@email.com";
        user.setEmail(new_email);
        assertEquals(user.getEmail(),new_email);
    }

    @Test
    public void testGetReminderFrequency(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        assertNull(user.getReminderFrequency());

    }

    @Test
    public void testSetReminderFrequency(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status,test_freq);
        String new_freq = "daily";
        user.setReminderFrequency(new_freq);
        assertEquals(user.getReminderFrequency(),new_freq);
    }



}
