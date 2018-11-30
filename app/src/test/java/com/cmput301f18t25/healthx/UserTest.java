/*
 *  * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the User class
 */

public class UserTest {

    public String test_name = "name";
    public String test_username = "usrname";
    public String test_phone_number = "1234567890";
    public String test_email = "patient@email.com";
    public String test_status = "patient";
    public String test_code = "12345";

    public UserTest(){}

    @Test
    public void testCloneUser(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);

        assertEquals(user.getName(),test_name);
        assertEquals(user.getUsername(),test_username);
        assertEquals(user.getPhoneNumber(),test_phone_number);
        assertEquals(user.getEmail(),test_email);
        assertEquals(user.getStatus(),test_status);

        User user2 = new User("name2","usrname2","0987654321","doctor@email.com","care provider");

        user.cloneUser(user2);

        assertEquals(user.getName(),"name2");
        assertEquals(user.getUsername(),"usrname2");
        assertEquals(user.getPhoneNumber(),"0987654321");
        assertEquals(user.getEmail(),"doctor@email.com");
        assertEquals(user.getStatus(),"care provider");

    }

    @Test
    public void testGetName(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        assertEquals(user.getName(),test_name);
    }

    @Test
    public void testSetName(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        String new_name = "new_name";
        user.setName(new_name);
        assertEquals(user.getName(),new_name);
    }

    @Test
    public void testGetUsername(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        assertEquals(user.getUsername(),test_username);

    }

    @Test
    public void testSetUsername(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        String new_username = "new_usrnm";
        user.setUsername(new_username);
        assertEquals(user.getUsername(),new_username);
    }

    @Test
    public void testGetPhoneNumber(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        assertEquals(user.getPhoneNumber(),test_phone_number);

    }

    @Test
    public void testSetPhoneNumber(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        String new_number = "7564536123";
        user.setPhoneNumber(new_number);
        assertEquals(user.getPhoneNumber(),new_number);
    }

    @Test
    public void testGetEmail(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        assertEquals(user.getEmail(),test_email);

    }

    @Test
    public void testSetEmail(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        String new_email = "new_email@email.com";
        user.setEmail(new_email);
        assertEquals(user.getEmail(),new_email);
    }

    @Test
    public void testGetReminderFrequency(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        assertNull(user.getReminderFrequency());

    }

    @Test
    public void testSetReminderFrequency(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        String new_freq = "daily";
        user.setReminderFrequency(new_freq);
        assertEquals(user.getReminderFrequency(),new_freq);
    }

    @Test
    public void testGetCode(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        assertNull(user.getCode());

    }

    @Test
    public void testSetCode(){
        User user = new User(test_name,test_username,test_phone_number,test_email,test_status);
        user.setCode(test_code);
        assertEquals(user.getCode(),test_code);
    }


}
