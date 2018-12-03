/*
 * Class Name: ProblemTest
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.Assert.*;


public class ProblemTest {

    public String test_title = "title";
    public String test_description = "description";
    public String test_date = "2018-10-1";
    public String test_id = "abc";
    public String test_problemID = "123e";
    @Mock
    Bitmap bitmap;



    public ProblemTest(){}

    @Test
    public void testGetTitle(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        assertEquals(problem.getTitle(),test_title);
    }

    @Test
    public void testSetTitle(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        String new_title = "new_title";
        problem.setTitle(new_title);
        assertEquals(problem.getTitle(),new_title);
    }

    @Test
    public void testGetDescription(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        assertEquals(problem.getDescription(),test_description);

    }

    @Test
    public void testSetDescription(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        String new_description = "new_description";
        problem.setDescription(new_description);
        assertEquals(problem.getDescription(),new_description);
    }

    @Test
    public void testGetDate(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        assertEquals(problem.getDate(),test_date);

    }

    @Test
    public void testSetDate(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        String new_date = "2018-09-31";
        problem.setDate(new_date);
        assertEquals(problem.getDate(),new_date);
    }

    @Test
    public void testGetRecordArray(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        assertTrue(problem.getRecordArray().isEmpty());

    }

    @Test
    public void testAddRecord(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        Record record = new Record("record_title","comment",
                10.0,10.0, bitmap, test_date,test_problemID);
        problem.addRecord(record);
        assertEquals(problem.getRecordArray().get(0),record);
    }

    @Test
    public void testDeleteRecord(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        Record record = new Record("record_title","comment",
                10.0,10.0, bitmap, test_date,test_problemID);
        problem.addRecord(record);
        assert(problem.getCount() == 1);

        problem.deleteRecord(record);
        assert(problem.getCount() == 0);

    }

    @Test
    public void testGetCount(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        assert(problem.getCount() == 0);

        Record record1 = new Record("record_title1","comment",
                10.0,10.0, bitmap, test_date,test_problemID);

        Record record2 = new Record("record_title2","comment",
                10.0,10.0, bitmap, test_date,test_problemID);

        problem.addRecord(record1);
        assert(problem.getCount() == 1);

        problem.addRecord(record2);
        assert(problem.getCount() == 2);

        problem.deleteRecord(record1);
        assert(problem.getCount() == 1);

    }

}
