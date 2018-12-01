package com.cmput301f18t25.healthx;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EditProfileTest extends ActivityTestRule<Login> {
    public String test_username = "usrname";
    public String test_title = "title";
    public String test_description = "description of problem";

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

        // first log in

        solo.assertCurrentActivity("wrong activity", Login.class);

        EditText id = (EditText) solo.getView(R.id.loginUserID);

        solo.enterText(id,test_username);
        solo.clickOnView(solo.getView(R.id.btn_login));

        solo.assertCurrentActivity("wrong activity", ViewProblemList.class);

        // click on edit profile button

        solo.clickOnActionBarHomeButton();
        solo.clickOnView(solo.getView(R.id.nav_edit));
        boolean next_view = solo.waitForActivity(EditUserProfile.class,3000);
        assertTrue("did not go to edit profile",next_view);

        // edit fields

        





    }


}
