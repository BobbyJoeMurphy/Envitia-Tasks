package com.example.envitiatask

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.envitiatask.ui.MainActivity
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAddLogEntry() {
        val testMessage = "Hello Android team!"

        onView(withId(R.id.inputField)).perform(typeText(testMessage), closeSoftKeyboard())

        onView(withId(R.id.okButton)).perform(click())

        onView(withId(R.id.logText)).check(matches(withText(containsString(testMessage))))
    }
}
