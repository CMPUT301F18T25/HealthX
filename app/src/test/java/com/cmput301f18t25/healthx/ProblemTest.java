/*
 *  * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Unit tests for the Problem class
 */


public class ProblemTest {

    public String test_title = "title";
    public String test_description = "description";
    public String test_date = "2018-10-1";

    public ProblemTest(){}

    @Test
    public void testGetTitle(){
        Problem problem = new Problem(test_title,test_description,test_date);
        assertEquals(problem.getTitle(),test_title);
    }

    @Test
    public void testSetTitle(){
        Problem problem = new Problem(test_title,test_description,test_date);
        String new_title = "new_title";
        problem.setTitle(new_title);
        assertEquals(problem.getTitle(),new_title);
    }

    @Test
    public void testGetDescription(){
        Problem problem = new Problem(test_title,test_description,test_date);
        assertEquals(problem.getDescription(),test_description);

    }

    @Test
    public void testSetDescription(){
        Problem problem = new Problem(test_title,test_description,test_date);
        String new_description = "new_description";
        problem.setDescription(new_description);
        assertEquals(problem.getDescription(),new_description);
    }

    @Test
    public void testGetDate(){
        Problem problem = new Problem(test_title,test_description,test_date);
        assertEquals(problem.getDate(),test_date);

    }

    @Test
    public void testSetDate(){
        Problem problem = new Problem(test_title,test_description,test_date);
        String new_date = "2018-09-31";
        problem.setDate(new_date);
        assertEquals(problem.getDate(),new_date);
    }

    @Test
    public void testGetRecordArray(){
        Problem problem = new Problem(test_title,test_description,test_date);
        assertTrue(problem.getRecordArray().isEmpty());

    }

    @Test
    public void testAddRecord(){
        Problem problem = new Problem(test_title,test_description,test_date);
        Record record = new Record("record_title","comment",
                10.0,10.0);
        problem.addRecord(record);
        assertEquals(problem.getRecordArray().get(0),record);
    }

}
