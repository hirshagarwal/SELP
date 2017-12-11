package com.example.hirshagarwal.songle;

import android.support.test.espresso.assertion.ViewAssertions;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Hirsh Agarwal on 12/11/2017.
 */

public class GameMapActivityTest {
    @Rule public final ActivityRule<GameMapActivity> gameMap = new ActivityRule<>(GameMapActivity.class);

    @Test
    public void guessScreenTest(){
        onView(withId(R.id.guess_fab)).perform(click());
        onView(withId(R.id.editText_song_guess)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void iconPressTest(){

    }


}
