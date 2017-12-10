package com.example.hirshagarwal.songle;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Hirsh Agarwal on 12/10/2017.
 */

public class MainActivityTest{

    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    // Test that the Designed in Edinburgh text shows
    @Test
    public void launchMainScreenTest(){
        onView(withText("Designed in Edinburgh")).check(ViewAssertions.matches(isDisplayed()));
    }

    // Verify that we can move to next screen
    @Test
    public void testButtonClick(){
        SystemClock.sleep(1000);
        onView(withId(R.id.start_game_button)).perform(click());
        onView(withId(R.id.map)).check(ViewAssertions.matches(isDisplayed()));
    }

    // Check that we can go to the scores screen
    @Test
    public void testScoresButton(){
        onView(withId(R.id.main_page_leaderboard_icon)).perform(click());
        onView(withId(R.id.topInformation)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.scores_home_button)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.scores_home_button)).perform(click());
        onView(withText("Designed in Edinburgh")).check(ViewAssertions.matches(isDisplayed()));
    }

    // Check that we can go to the settings screen
    @Test
    public void testSettingsButton(){
        onView(withId(R.id.main_page_settings_icon)).perform(click());
        onView(withId(R.id.toolbar_settings)).check(ViewAssertions.matches(isDisplayed()));
    }
}
