/*
 *  * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Record class
 */

public class RecordTest {

    public String test_title = "title";
    public String test_comment = "comment";
    public Double test_lat = 1.0;
    public Double test_long = 1.0;

    public RecordTest(){}

    @Test
    public void testGetTitle(){
        Record record = new Record(test_title,test_comment,test_lat,test_long);
        assertEquals(record.getTitle(),test_title);
    }

    @Test
    public void testSetTitle(){
        Record record = new Record(test_title,test_comment,test_lat,test_long);
        String new_title = "new_title";
        record.setTitle(new_title);
        assertEquals(record.getTitle(),new_title);
    }

    @Test
    public void testGetComment(){
        Record record = new Record(test_title,test_comment,test_lat,test_long);
        assertEquals(record.getComment(),test_comment);

    }

    @Test
    public void testSetComment(){
        Record record = new Record(test_title,test_comment,test_lat,test_long);
        String new_comment = "new_comment";
        record.setComment(new_comment);
        assertEquals(record.getComment(),new_comment);
    }

    @Test
    public void testGetLatitude(){
        Record record = new Record(test_title,test_comment,test_lat,test_long);
        assertEquals(record.getLatitude(),test_lat);

    }

    @Test
    public void testSetLatitude(){
        Record record = new Record(test_title,test_comment,test_lat,test_long);
        Double new_lat = 2.0;
        record.setLatitude(new_lat);
        assertEquals(record.getLatitude(),new_lat);
    }

    @Test
    public void testGetLongitude(){
        Record record = new Record(test_title,test_comment,test_lat,test_long);
        assertEquals(record.getLongitude(),test_long);

    }

    @Test
    public void testSetLongitude(){
        Record record = new Record(test_title,test_comment,test_lat,test_long);
        Double new_long = 2.0;
        record.setLongitude(new_long);
        assertEquals(record.getLongitude(),new_long);
    }


}
