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

public class ProblemListTest {

    public String test_title = "title";
    public String test_description = "description";
    public String test_date = "2018-10-1";
    public String test_id = "abc";

    public ProblemList problemList = ProblemList.getInstance();

    public ProblemListTest(){}

    @Test
    public void testAddToProblemList(){

        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        problemList.addToProblemList(problem);
        assertEquals(problemList.getListCount(),1);
        assertEquals(problemList.getElementByIndex(0),problem);
        problemList.removeProblemFromList(0);

    }

    @Test
    public void testEditProblem(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        problemList.addToProblemList(problem);
        assertEquals(problemList.getElementByIndex(0),problem);

        String new_title = "new title";
        Problem editedProblem = new Problem(new_title,test_description,test_date,test_id);
        problemList.EditProblem(0,editedProblem);
        assertEquals(problemList.getElementByIndex(0).getTitle(),new_title);
        problemList.removeProblemFromList(0);

    }

    @Test
    public void testGetListCount(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);

        problemList.addToProblemList(problem);
        problemList.addToProblemList(problem);

        assertEquals(problemList.getListCount(),2);

        problemList.addToProblemList(problem);
        problemList.addToProblemList(problem);

        assertEquals(problemList.getListCount(),4);

        problemList.removeProblemFromList(1);
        assertEquals(problemList.getListCount(),3);
        problemList.removeProblemFromList(0);
        problemList.removeProblemFromList(0);
        problemList.removeProblemFromList(0);

    }

    @Test
    public void testGetElementByIndex(){
        Problem problem1 = new Problem(test_title,test_description,test_date,test_id);
        Problem problem2 = new Problem(test_title+"2",test_description+"2",test_date,test_id+"2");

        problemList.addToProblemList(problem1);
        problemList.addToProblemList(problem2);
        assertEquals(problemList.getElementByIndex(0),problem1);
        assertEquals(problemList.getElementByIndex(1),problem2);

        problemList.removeProblemFromList(0);
        assertEquals(problemList.getElementByIndex(0),problem2);
        problemList.removeProblemFromList(0);
    }

    @Test
    public void testRemoveProblemFromList(){
        Problem problem = new Problem(test_title,test_description,test_date,test_id);
        problemList.addToProblemList(problem);
        assertEquals(problemList.getListCount(),1);

        problemList.removeProblemFromList(0);
        assertEquals(problemList.getListCount(),0);

    }

}
