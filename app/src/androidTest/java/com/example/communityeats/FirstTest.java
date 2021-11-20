package com.example.communityeats;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;
import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.communityeats.activities.RegisterActivity;

import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class FirstTest {

    /*

    ----------------------------------------

    THIS IS THE BASIC SETUP FOR HOW A TEST CASE SHOULD BE:
        @RULE
        -LAUNCHING THE ACTIVITY TO TEST WHEN YOU START THE APPLICATION
        @BEFORE
        -INITIALIZE ANY DATA BEFORE EACH TEST CASE RUNS. DOING A SPECIFIC ACTION BEFORE A TEST CASE
        @TEST
        -TESTING THE APPLICATION
        @AFTER
         -THE THINGS WE DO AFTER EVERY TEST CASE


    -----------------------------------------


     */


    /*
    Bascially this here says what activity should run when we start the application
        -here we are testing the register activity
        */
//    @Rule
//    public ActivityTestRule<RegisterActivity> rActivityRule = new ActivityTestRule<>(RegisterActivity.class);
//
//    /*
//
//     */
//    @Before
//    public void setUp() throws Exception {
//
//    }
//
//    @Test
//    public void testRegisterActivity() {
//        onView(withId(R.id.btnRegister)).perform(click());
////        onView(withId(R.id.input_username_register)).check(matches(withText("")))
//
//
//    }


//    public void useAppContext() {
////        // Context of the app under test.
////        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
////        assertEquals("com.example.communityeats", appContext.getPackageName());
//    }
}
