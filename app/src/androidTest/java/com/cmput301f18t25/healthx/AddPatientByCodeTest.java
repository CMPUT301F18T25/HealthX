/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;


import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
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

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class AddPatientByCodeTest extends ActivityTestRule<Login> {
    //patient info
    public String testPatientUsername = "usrname"+RandomStringUtils.randomAlphabetic(3);
    public String testPatientName = "name"+RandomStringUtils.randomAlphabetic(3);
    public String testPatientPhoneNumber = "5467658769";
    public String testPatientEmail = testPatientUsername+"@email.com";

    //provider info
    public String testProviderUsername = "usrname"+RandomStringUtils.randomAlphabetic(3);
    public String testProviderName = "name"+RandomStringUtils.randomAlphabetic(3);
    public String testProviderPhoneNumber = "5467658769";
    public String testProviderEmail = testProviderUsername+"@email.com";

    // make a dif title each time we test it, so we're not mixing up problems
    public String test_title = "title"+RandomStringUtils.randomAlphabetic(3);
    public String test_description = "description of problem"+RandomStringUtils.randomAlphabetic(3);
    Random random = new Random();
    public Integer test_year = random.nextInt(2018-1970) + 1970;
    public Integer test_month = random.nextInt(12-1) + 1;
    public Integer test_day = random.nextInt(30-1)+1;

    public int wait_time = 3000; // 3 seconds

    private Solo solo;

    private void swipeToRight(Solo solo) {
        Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        float xStart = 0;
        float xEnd = width / 2;
        solo.drag(xStart, xEnd, height / 2, height / 2, 10);
        return;
    }
    public AddPatientByCodeTest() {
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
    public void TestAddPatientByCode () throws Exception {
        //Sign up Patient
        solo.assertCurrentActivity("wrong activity",Login.class);
        solo.clickOnView(solo.getView(R.id.link_signup));
        assertTrue("wrong activity", solo.waitForActivity(Signup.class));

        EditText patient_id = (EditText) solo.getView(R.id.input_id);
        EditText patient_name = (EditText) solo.getView(R.id.input_name);
        EditText patient_email = (EditText) solo.getView(R.id.input_email);
        EditText patient_phone = (EditText) solo.getView(R.id.input_phone);
        RadioButton patient_btn = (RadioButton) solo.getView(R.id.radio_patient);

        solo.enterText(patient_id,testPatientUsername);
        solo.enterText(patient_name,testPatientName);
        solo.enterText(patient_email,testPatientEmail);
        solo.enterText(patient_phone,testPatientPhoneNumber);
        solo.clickOnView(patient_btn);
        solo.clickOnView(solo.getView(R.id.btn_signup));

        solo.waitForActivity(ViewProblemList.class);
        //Open Sidebar and Gen code
        swipeToRight(solo);
        solo.clickOnText("Generate share code");
        //solo.setNavigationDrawer(Solo.OPENED);
        //solo.clickOnActionBarHomeButton();
        //solo.clickOnView(solo.getView(R.id.nav_code));
        solo.waitForActivity(ActivityGenerateCode.class);
        solo.clickOnView(solo.getView(R.id.btn_generate));

        TextView output_view = (TextView) solo.getView(R.id.code_output);
        String code = output_view.getText().toString();
        Log.d("CDE",code);
        solo.goBack();

        //Logout Patient
        swipeToRight(solo);
        solo.clickOnText("Log out");

        // Sign up CP
        solo.clickOnView(solo.getView(R.id.link_signup));
        assertTrue("wrong activity", solo.waitForActivity(Signup.class));

        EditText cp_id = (EditText) solo.getView(R.id.input_id);
        EditText cp_name = (EditText) solo.getView(R.id.input_name);
        EditText cp_email = (EditText) solo.getView(R.id.input_email);
        EditText cp_phone = (EditText) solo.getView(R.id.input_phone);
        RadioButton cp_btn = (RadioButton) solo.getView(R.id.radio_provider);

        solo.enterText(cp_id,testProviderUsername);
        solo.enterText(cp_name,testProviderName);
        solo.enterText(cp_email,testProviderEmail);
        solo.enterText(cp_phone,testProviderPhoneNumber);
        solo.clickOnView(cp_btn);
        solo.clickOnView(solo.getView(R.id.btn_signup));

        // log in CP

        /*
        EditText id_input = (EditText) solo.getView(R.id.loginUserID);

        solo.enterText(id_input,testProviderUsername);
        solo.wait(wait_time);
        solo.clickOnView(solo.getView(R.id.btn_login));

        */
        assertTrue("did not log in",solo.waitForActivity(ViewPatientList.class));

        // choose to add a Patient

        solo.clickOnView(solo.getView(R.id.fab));
        assertTrue("did not go to add problem",solo.waitForActivity(ActivityAddPatient.class));

        // click on add by code

        solo.clickOnView(solo.getView(R.id.link_add_code));
        EditText addPatient_code = (EditText) solo.getView(R.id.code_input);
        solo.enterText(addPatient_code,code);

        // Add patient and go to patient list

        solo.clickOnView(solo.getView(R.id.btnAddPatientbyCode));

        assertTrue("did not go back to Patient list",solo.waitForActivity(ViewPatientList.class));
        assertTrue("Patient name not shown",solo.waitForText(testPatientName,1,5000,true));
        assertTrue("Patient not shown",solo.waitForText(testPatientUsername,1,5000,true));
        assertTrue("Patient name not shown",solo.waitForText(testPatientEmail,1,5000,true));
        assertTrue("Patient name not shown",solo.waitForText(testPatientPhoneNumber,1,5000,true));

    }
}