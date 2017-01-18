package com.app.ruoyu.gourmet;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTestSample {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);
    private String mStringToBetyped;

    @Before
    public void initValidString() {
        mStringToBetyped = "Espresso";
    }

    @Test
    public void checkYelp() {
        // Press button
        onView(withText("Yelp")).perform(click()).check(matches(withText(mStringToBetyped)));
    }

    @Test
    public void checkBackend() {
        // Press button
        onView(withId(R.id.button_backend)).perform(click()).check(matches(withText("New text")));
    }
}