/*
 *  * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.graphics.Bitmap;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;

import java.util.Date;

/**
 * Unit tests for the Record class
 */

public class RecordTest {

    public String test_title = "title";
    public String test_comment = "comment";
    public Double test_lat = 1.0;
    public Double test_long = 1.0;
    public String test_date = "2018-10-1";
    public String test_problemID = "123e";
    @Mock
    Bitmap test_bitmap;
    @Mock
    Bitmap new_bitmap;


    public RecordTest(){}

    @Test
    public void testGetTitle(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        assertEquals(record.getTitle(),test_title);
    }

    @Test
    public void testSetTitle(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        String new_title = "new_title";
        record.setTitle(new_title);
        assertEquals(record.getTitle(),new_title);
    }

    @Test
    public void testGetComment(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        assertEquals(record.getComment(),test_comment);

    }

    @Test
    public void testSetComment(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        String new_comment = "new_comment";
        record.setComment(new_comment);
        assertEquals(record.getComment(),new_comment);
    }

    @Test
    public void testGetLatitude(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        assertEquals(record.getLatitude(),test_lat);

    }

    @Test
    public void testSetLatitude(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        Double new_lat = 2.0;
        record.setLatitude(new_lat);
        assertEquals(record.getLatitude(),new_lat);
    }

    @Test
    public void testGetLongitude(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        assertEquals(record.getLongitude(),test_long);

    }

    @Test
    public void testSetLongitude(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        Double new_long = 2.0;
        record.setLongitude(new_long);
        assertEquals(record.getLongitude(),new_long);
    }

    @Test
    public void testSetDate(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        String new_date = "2018-10-02";
        record.setDate(new_date);
        assertEquals(new_date, record.getDate());
    }

    @Test
    public void testGetDate(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        assertEquals(test_date, record.getDate());
    }

    @Test
    public void testSetImage(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        record.setImage(new_bitmap);
        assertEquals(new_bitmap,record.getImage());
    }

    @Test
    public void testGetImage(){
        Record record = new Record(test_title,test_comment,test_lat,test_long, test_bitmap, test_date,test_problemID);
        assertEquals(test_bitmap,record.getImage());

    }



}
