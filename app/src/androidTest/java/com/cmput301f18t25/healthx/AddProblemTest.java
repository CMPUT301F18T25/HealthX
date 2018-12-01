package com.cmput301f18t25.healthx;


import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddProblemTest extends ActivityTestRule<Login> {

    public String test_username = "usrname";
    public String test_title = "title";
    public String test_description = "description of problem";

    private Solo solo;


    public AddProblemTest() {
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
        assertTrue("did not go to add problem",next_view);

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
        assertTrue("did not go to problem list",next_view4);

    }
}
