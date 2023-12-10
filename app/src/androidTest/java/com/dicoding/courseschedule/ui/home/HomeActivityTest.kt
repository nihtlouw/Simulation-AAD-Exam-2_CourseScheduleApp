package com.dicoding.courseschedule.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
        Intents.init()
    }

    @Test
    fun testAddActivityDisplayedAndVerifyComponent(){
        Espresso.onView(withId(R.id.action_add)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(AddCourseActivity::class.java.name))
        Espresso.onView(withId(R.id.ed_course_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.spinner_day))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.ed_start_time))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.ed_end_time))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.ed_lecturer))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.ed_note))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}