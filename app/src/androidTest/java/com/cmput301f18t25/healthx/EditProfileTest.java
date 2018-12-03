/*
 * Class Name: EditProfileTest
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.graphics.PointF;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.Display;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EditProfileTest extends ActivityTestRule<Login> {
    public String test_username = "usrname"+RandomStringUtils.randomAlphanumeric(3);
    public String test_name = "name"+RandomStringUtils.randomAlphanumeric(3);
    public String test_email = "email@patient.com";
    public String test_phone_number = "2344321456";

    private Solo solo;


    public EditProfileTest() {
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
    public void testEdit() throws Exception {

        // make a new account

        solo.assertCurrentActivity("wrong activity",Login.class);
        solo.clickOnView(solo.getView(R.id.link_signup));


        solo.assertCurrentActivity("wrong activity", Signup.class);


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


        boolean next_view2 = solo.waitForActivity(ViewProblemList.class);
        assertTrue("did not log in",next_view2);

        // the problem with toast is sometimes it shows up and leaves so fast
        // solo doesn't detect it -- I've verified this by watching as the toast shows up
        // in the test and then watching solo say "toast not shown"

        //assertTrue("toast not shown",solo.waitForText(test_name,1,5000));

        // click on edit profile button

        Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        float xStart = 0 ;
        float xEnd = width / 2;
        solo.drag(xStart, xEnd, height / 2, height / 2, 10);
        solo.clickOnText("Edit");

        boolean next_view3 = solo.waitForActivity(EditUserProfile.class,5000);
        assertTrue("did not go to edit profile",next_view3);

        // make sure current data shown

        TextView us_id = (TextView) solo.getView(R.id.edit_id);
        EditText edit_name = (EditText) solo.getView(R.id.edit_name);
        EditText edit_email = (EditText) solo.getView(R.id.edit_email);
        EditText edit_phone = (EditText) solo.getView(R.id.edit_phone);

        assertEquals(us_id.getText().toString(),test_username);
        assertEquals(edit_name.getText().toString(),test_name);
        assertEquals(edit_email.getText().toString(),test_email);
        assertEquals(edit_phone.getText().toString(),test_phone_number);
        assertTrue(solo.isSpinnerTextSelected("None"));


        // edit the fields

        String new_name = "new_name";
        String new_email = "new@patient.com";
        String new_phone = "0987678909";

        solo.clearEditText(edit_name);
        solo.clearEditText(edit_email);

        solo.clearEditText(edit_phone);

        solo.enterText(edit_name,new_name);
        solo.enterText(edit_email,new_email);
        solo.enterText(edit_phone,new_phone);

        solo.clickOnView(solo.getView(R.id.frequency_menu));
        solo.clickOnText("Everyday");

        solo.clickOnView(solo.getView(R.id.save_button));
        solo.assertCurrentActivity("wrong activity", ViewProblemList.class);
        solo.sleep(3000);
        //assertTrue("toast not shown",solo.waitForText("Profile Edited",1,5000));


        // check that fields were changed

        solo.drag(xStart, xEnd, height / 2, height / 2, 10);
        solo.clickOnText("Edit");
        boolean next_view4 = solo.waitForActivity(EditUserProfile.class);
        assertTrue("did not go to edit profile",next_view4);

        assertEquals(edit_name.getText().toString(),new_name);
        assertEquals(edit_email.getText().toString(),new_email);
        assertEquals(edit_phone.getText().toString(),new_phone);
        assertTrue(solo.searchText("Everyday"));
        solo.goBack();
        solo.sleep(3000);

        // try logging in

        display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        xEnd = width / 2;
        solo.drag(xStart, xEnd, height / 2, height / 2, 10);
        solo.clickOnText("Log out");

        boolean next_view5 = solo.waitForActivity(Login.class);
        assertTrue("did not go to login",next_view5);

        EditText username = (EditText) solo.getView(R.id.loginUserID);
        solo.clearEditText(username);
        solo.enterText(username,test_username);

        solo.clickOnView(solo.getView(R.id.btn_login));


        boolean next_view6 = solo.waitForActivity(ViewProblemList.class);
        assertTrue("did not log in",next_view6);
        assertTrue("toast not shown",solo.waitForText(new_name,1,3000));
















    }


}
