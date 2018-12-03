/*
 * Class Name: AddRecordTest
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;


import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.widget.DatePicker;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AddRecordTest extends ActivityTestRule<Login> {

    public String test_username = "usrname";
    public String test_title = "title";
    public String test_description = "description of problem";

    private Solo solo;


    public AddRecordTest() {
        super(Login.class);
    }

    @Rule
    public ActivityTestRule<Login> activityTestRule =
            new ActivityTestRule<>(Login.class);


    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddProblem() throws Exception {

        // log in

        solo.assertCurrentActivity("wrong activity", Login.class);

        EditText id = (EditText) solo.getView(R.id.loginUserID);

        solo.enterText(id,test_username);
        solo.clickOnView(solo.getView(R.id.btn_login));

        solo.assertCurrentActivity("wrong activity", ViewProblemList.class);

        // choose to add a problem

        solo.clickOnView(solo.getView(R.id.fab));
        boolean next_view = solo.waitForActivity(ActivityAddProblem.class,3000);
        assertTrue(next_view);

        // fill in problem details

        EditText title = (EditText) solo.getView(R.id.title_input);
        DatePicker date = (DatePicker) solo.getView(R.id.dateStarted_input);
        EditText description = (EditText) solo.getView(R.id.description_input);

        solo.enterText(title,test_title);
        solo.setDatePicker(date, 2018, 11, 2);
        solo.enterText(description,test_description);


        // save problem and go to problem list

        solo.clickOnView(solo.getView(R.id.save_button));
        boolean next_view4 = solo.waitForActivity(ViewProblemList.class,3000);
        assertTrue(next_view4);

        solo.clickOnText(test_title,0,true);
    }
}
