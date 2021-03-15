package com.example.hinotes

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListNoteUITest {
    @Rule
    val mainActivity: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    //object
    val recyclerView: RecyclerView = mainActivity.activity.findViewById(R.id.rcViewStore)

    @Test
    fun listNoteShowTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.example.hinotes", appContext.packageName)

    }
}
