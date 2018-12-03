/*
 *  * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package com.cmput301f18t25.healthx;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class LoginWithCodeTest extends ActivityTestRule<Login>{

    public String test_name = "name"+RandomStringUtils.randomAlphabetic(3);

    // make a dif username each time we test it, so we're not mixing up users
    public String test_username = "usrname"+RandomStringUtils.randomAlphanumeric(3);
    public String test_phone_number = "1234567890";
    public String test_email = test_username+"@email.com";
    private Solo solo;


    public LoginWithCodeTest() {
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
    public void testLoginCode() throws Exception {

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
        assertTrue("not logged in",solo.waitForActivity(ViewProblemList.class, 3000));

        // go to generate new code

        solo.clickOnActionBarHomeButton();
        solo.clickOnView(solo.getView(R.id.nav_code));
        TextView new_code = (TextView) solo.getText(R.id.code_output);
        String code = new_code.getText().toString();

        // log out and back in with code
        solo.clickOnActionBarHomeButton();
        assertTrue("not back to list",solo.waitForActivity(ViewProblemList.class, 3000));

        solo.clickOnActionBarHomeButton();
        solo.clickOnView(solo.getView(R.id.nav_logout));


        assertTrue(solo.waitForActivity(Login.class));
        solo.clickOnView(solo.getView(R.id.link_code_login));
        assertTrue("didn't go to code login",solo.waitForActivity(CodeLogin.class, 3000));



        EditText user_code = (EditText) solo.getView(R.id.loginUserCode);
        solo.enterText(user_code,code);
        solo.clickOnView(solo.getView(R.id.btn_login));

        boolean next_view = solo.waitForActivity(ViewProblemList.class, 3000);
        assertTrue("not logged in",next_view);

        // now the code shouldn't work since it is one time
        solo.clickOnActionBarHomeButton();
        solo.clickOnView(solo.getView(R.id.nav_logout));
        assertTrue(solo.waitForActivity(Login.class));
        solo.clickOnView(solo.getView(R.id.link_code_login));
        assertTrue("didn't go to code login",solo.waitForActivity(CodeLogin.class, 3000));
        solo.enterText(user_code,code);
        solo.clickOnView(solo.getView(R.id.btn_login));

        solo.clickOnView(solo.getView(R.id.btn_login));
        assertTrue("toast not shown",solo.waitForText("Invalid Code!",1,3000));


    }

}