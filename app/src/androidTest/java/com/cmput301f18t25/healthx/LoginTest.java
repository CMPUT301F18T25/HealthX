package com.cmput301f18t25.healthx;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import android.widget.EditText;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;


public class LoginTest extends ActivityTestRule<Login>{

    public String test_username = "usrname";
    private Solo solo;


    public LoginTest() {
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
    public void testLogin() throws Exception {

        solo.assertCurrentActivity("wrong activity", Login.class);


        EditText id = (EditText) solo.getView(R.id.loginUserID);

        solo.clickOnView(solo.getView(R.id.btn_login));
        assertTrue("toast not shown",solo.waitForText("Invalid Credentials!",1,3000));

        solo.enterText(id,test_username);
        solo.clickOnView(solo.getView(R.id.btn_login));

        boolean next_view = solo.waitForActivity(ViewProblemList.class, 3000);
        assertTrue("not logged in",next_view);

    }

}