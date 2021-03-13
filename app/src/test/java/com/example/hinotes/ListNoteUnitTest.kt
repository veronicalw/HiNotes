package com.example.hinotes

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.example.hinotes.core.main_activity.MainActivityContract
import com.example.hinotes.core.main_activity.MainActivityPresenter
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@MediumTest
class ListNoteUnitTest {

    @get:Rule
    val main: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    val context: Context = Mockito.mock(Context::class.java)
    val intSizeItem = 5
    val recyclerView = main.activity.findViewById<RecyclerView>(R.id.rcViewStore) as RecyclerView

    //Calling contract and presenter
    val interactor = Mockito.mock(MainActivityContract.Interactor::class.java)
    lateinit var mPresenter: MainActivityPresenter

    @Test
    fun testListNotes() {
        mPresenter.readNotes(context, recyclerView)
        Mockito.verify(interactor, times(1)).performReadNotes(context, recyclerView)
        assertThat(recyclerView.scrollBarSize, `is`(intSizeItem))
    }
}