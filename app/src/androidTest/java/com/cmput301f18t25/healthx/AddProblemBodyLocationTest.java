/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;


import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.robotium.solo.Solo;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class AddProblemBodyLocationTest extends ActivityTestRule<Login> {

    public String test_username = "usrname"+RandomStringUtils.randomAlphabetic(3);
    public String test_name = "name"+RandomStringUtils.randomAlphabetic(3);
    public String test_phone_number = "5467658769";
    public String test_email = test_username+"@email.com";

    // make a dif title each time we test it, so we're not mixing up problems
    public String test_title = "title"+RandomStringUtils.randomAlphabetic(3);
    public String test_description = "description of problem"+RandomStringUtils.randomAlphabetic(3);
    Random random = new Random();
    public Integer test_year = random.nextInt(2018-1970) + 1970;
    public Integer test_month = random.nextInt(12-1) + 1;
    public Integer test_day = random.nextInt(30-1)+1;

    public int wait_time = 3000; // 3 seconds

    private Solo solo;


    public AddProblemBodyLocationTest() {
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
    public void testAddBodyLoc() throws Exception {

        // first make a new account

        solo.assertCurrentActivity("wrong activity",Login.class);
        solo.clickOnView(solo.getView(R.id.link_signup));
        assertTrue("wrong activity", solo.waitForActivity(Signup.class));

        EditText id = (EditText) solo.getView(R.id.input_id);
        EditText name = (EditText) solo.getView(R.id.input_name);
        EditText email = (EditText) solo.getView(R.id.input_email);
        EditText phone = (EditText) solo.getView(R.id.input_phone);
        RadioButton patient_btn = (RadioButton) solo.getView(R.id.radio_patient);

        solo.enterText(id,test_username);
        solo.enterText(name,test_name);
        solo.enterText(email,test_email);
        solo.enterText(phone,test_phone_number);
        solo.clickOnView(patient_btn);
        solo.clickOnView(solo.getView(R.id.btn_signup));


        // commented this out bc signup used to redirect to login but now logs in immediately
        /*assertTrue("did not go to login", solo.waitForActivity(Login.class));

        // log in

        EditText id_input = (EditText) solo.getView(R.id.loginUserID);

        solo.enterText(id_input,test_username);
        solo.sleep(wait_time);
        solo.clickOnView(solo.getView(R.id.btn_login));*/


        assertTrue("did not log in",solo.waitForActivity(ViewProblemList.class));

        // choose to add a problem

        solo.clickOnView(solo.getView(R.id.fab));
        assertTrue("did not go to add problem",solo.waitForActivity(ActivityAddProblem.class));

        // fill in problem details

        EditText title = (EditText) solo.getView(R.id.title_input);
        DatePicker date = (DatePicker) solo.getView(R.id.dateStarted_input);
        EditText description = (EditText) solo.getView(R.id.description_input);

        solo.enterText(title,test_title);
        solo.setDatePicker(date, test_year,test_month,test_day);
        solo.enterText(description,test_description);

        // choose to add body loc
        solo.clickOnView(solo.getView(R.id.record_photo));
        assertTrue("did not go to add body loc",solo.waitForActivity(ActivityBodyLocation.class));


        ImageView doll = (ImageView) solo.getView(R.id.body_image);
        int width = doll.getMeasuredWidth();
        int height = doll.getMeasuredHeight();

        // check that body locations are accurate for front

        solo.clickOnScreen((float) ((0.34*width)-0.001),(float) ((0.58*height)-0.001));
        assertTrue("Front Left Arm not shown",solo.searchText("Front Left Arm",true));

        solo.clickOnScreen((float) ((width)-0.001),(float) ((0.58*height)-0.001));
        assertTrue("Front Right Arm not shown",solo.searchText("Front Right Arm",true));

        solo.clickOnScreen((float) ((0.75*width)-0.001),(float) ((0.356*height)-0.001));
        assertTrue("Head not shown",solo.searchText("Head",true));

        solo.clickOnScreen((float) ((0.67*width)-0.001),(float) ((0.58*height)-0.001));
        assertTrue("Chest not shown",solo.searchText("Chest",true));

        solo.clickOnScreen((float) ((0.5*width)-0.001),(float) ((0.974*height)-0.001));
        assertTrue("Front Left Leg not shown",solo.searchText("Front Left Leg",true));

        solo.clickOnScreen((float) ((0.973*width)-0.001),(float) ((0.974*height)-0.001));
        assertTrue("Front Right Leg not shown",solo.searchText("Front Right Leg",true));

        solo.clickOnScreen((float) ((0.81*width)-0.001),(float) ((0.974*height)-0.001));
        assertTrue("Waist not shown",solo.searchText("Waist",true));

        // check that body locations are accurate for back
        solo.clickOnView(solo.getView(R.id.changeBodyViewButton));

        solo.clickOnScreen((float) ((0.34*width)-0.001),(float) ((0.58*height)-0.001));
        assertTrue("Back Left Arm not shown",solo.searchText("Back Left Arm",true));

        solo.clickOnScreen((float) ((width)-0.001),(float) ((0.58*height)-0.001));
        assertTrue("Back Right Arm not shown",solo.searchText("Back Right Arm",true));

        solo.clickOnScreen((float) ((0.75*width)-0.001),(float) ((0.356*height)-0.001));
        assertTrue("Head not shown",solo.searchText("Head",true));

        solo.clickOnScreen((float) ((0.67*width)-0.001),(float) ((0.58*height)-0.001));
        assertTrue("Back not shown",solo.searchText("Back",true));

        solo.clickOnScreen((float) ((0.5*width)-0.001),(float) ((0.974*height)-0.001));
        assertTrue("Back Left Leg not shown",solo.searchText("Back Left Leg",true));

        solo.clickOnScreen((float) ((0.973*width)-0.001),(float) ((0.974*height)-0.001));
        assertTrue("Back Right Leg not shown",solo.searchText("Back Right Leg",true));

        solo.clickOnScreen((float) ((0.81*width)-0.001),(float) ((0.974*height)-0.001));
        assertTrue("Butt not shown",solo.searchText("Butt",true));

        // save body location
        solo.clickOnView(solo.getView(R.id.saveBodyButton));
        assertTrue("did not go to problem add",solo.waitForActivity(ActivityAddProblem.class));
        assertTrue(solo.searchText("Butt",1,true));

        // save problem and go to problem list

        solo.clickOnView(solo.getView(R.id.save_button));
        assertTrue("did not go to problem list",solo.waitForActivity(ViewProblemList.class));

        // make sure the problem can be seen on the screen

        assertTrue("problem not shown",solo.waitForText(test_title,1,5000,true));
        assertTrue("problem desc not shown",solo.waitForText(test_description,1,5000,true));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(test_year, test_month, test_day);
        Date to_show = cal.getTime();
        String display_date = simpleDateFormat.format(to_show);
        Log.i("date",display_date + "    " + test_year +" "+ test_month+" " + test_day);


        assertTrue("date not shown",solo.waitForText(Pattern.quote(display_date),1,5000,true));

    }
}
