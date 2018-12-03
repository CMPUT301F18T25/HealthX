/*
 * Class Name: RequestCodeTest
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestCodeTest {

    public String test_username = "usrname";
    public String test_code = "12345";

    public RequestCodeTest(){}


    @Test
    public void testGetUsername(){
        RequestCode rc = new RequestCode(test_username,test_code);
        assertEquals(rc.getUsername(),test_username);

    }

    @Test
    public void testSetUsername(){
        RequestCode rc = new RequestCode(test_username,test_code);
        String new_username = "new_usrnm";
        rc.setUsername(new_username);
        assertEquals(rc.getUsername(),new_username);
    }


    @Test
    public void testGetCode(){
        RequestCode rc = new RequestCode(test_username,test_code);
        assertEquals(rc.getCode(),test_code);

    }

    @Test
    public void testSetCode(){
        RequestCode rc = new RequestCode(test_username,test_code);
        String new_code = "54321";
        rc.setCode(new_code);
        assertEquals(rc.getCode(),new_code);
    }

}
