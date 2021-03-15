package com.example.hinotes

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.example.hinotes.core.addnote_activity.AddNoteContract
import com.example.hinotes.core.addnote_activity.AddNotePresenter
import com.example.hinotes.core.main_activity.MainActivityContract
import com.example.hinotes.core.main_activity.MainActivityPresenter
import com.example.hinotes.core.main_activity.NotesViewHolder
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@MediumTest
class ListNoteUnitTest {

//    val main: Activity = Mockito.mock(MainActivity::class.java)
    val view = Mockito.mock(MainActivityContract.View::class.java)
    val context: Context = Mockito.mock(Context::class.java)
    val intSizeItem = 0
    val recyclerView : RecyclerView = Mockito.mock(RecyclerView::class.java)

    //Calling contract and presenter
    val interactor = Mockito.mock(MainActivityContract.Interactor::class.java)
    lateinit var mPresenter: MainActivityPresenter

    @Before
    fun initialize(){
        mPresenter = MainActivityPresenter(view,interactor)
        Mockito.doNothing().`when`(interactor).performReadNotes(context,recyclerView)
    }

    @Test
    fun testListNotes() {
        mPresenter.readNotes(context, recyclerView)
        Mockito.verify(interactor, times(1)).performReadNotes(context, recyclerView)
        assertThat(recyclerView.scrollBarSize, `is`(intSizeItem))
    }
}