package com.example.hinotes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.hinotes.CustomMatchers.Companion.withItemCount
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListNoteUITest {
    @Rule
    @JvmField
    val mainActivity: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun listNoteShowTest() {
        onView(withId(R.id.rcViewStore))
            .check(matches(withItemCount(3)))
    }
}
