package com.example.communityeats.activities;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.communityeats.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest3 {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void registerActivityTest3() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.signup), withText("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        textView.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.input_username_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.input_email_registration),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText2.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.input_username_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText3.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.input_password_registration),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        editText4.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.input_username_register), withText("a"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText5.perform(replaceText("aaa"));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.input_username_register), withText("aaa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText6.perform(closeSoftKeyboard());

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.input_email_registration), withText("a"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText7.perform(replaceText("aaaa@gm"));

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.input_email_registration), withText("aaaa@gm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText8.perform(closeSoftKeyboard());

        ViewInteraction editText9 = onView(
                allOf(withId(R.id.input_password_registration), withText("a"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        editText9.perform(replaceText(""));

        ViewInteraction editText10 = onView(
                allOf(withId(R.id.input_password_registration),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        editText10.perform(closeSoftKeyboard());

        ViewInteraction editText11 = onView(
                allOf(withId(R.id.input_email_registration), withText("aaaa@gm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText11.perform(replaceText("aaaa@gmai"));

        ViewInteraction editText12 = onView(
                allOf(withId(R.id.input_email_registration), withText("aaaa@gmai"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText12.perform(closeSoftKeyboard());

        ViewInteraction editText13 = onView(
                allOf(withId(R.id.input_password_registration),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        editText13.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction editText14 = onView(
                allOf(withId(R.id.input_email_registration), withText("aaaa@gmai"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText14.perform(replaceText("aaaa@gmail.com"));

        ViewInteraction editText15 = onView(
                allOf(withId(R.id.input_email_registration), withText("aaaa@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText15.perform(closeSoftKeyboard());

        pressBack();

        ViewInteraction editText16 = onView(
                allOf(withId(R.id.input_address_registration),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        editText16.perform(replaceText("San Jose State universi"), closeSoftKeyboard());

        pressBack();

        ViewInteraction editText17 = onView(
                allOf(withId(R.id.input_address_registration), withText("San Jose State universi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        editText17.perform(replaceText("San Jose State University"));

        ViewInteraction editText18 = onView(
                allOf(withId(R.id.input_address_registration), withText("San Jose State University"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        editText18.perform(closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        button.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
