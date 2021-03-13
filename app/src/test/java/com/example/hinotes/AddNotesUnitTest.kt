package com.example.hinotes

import android.content.Context
import com.example.hinotes.core.addnote_activity.AddNoteContract
import com.example.hinotes.core.addnote_activity.AddNotePresenter
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddNotesUnitTest {
    //Object Mock
    val titles = "note1"
    val contents = "contents1"
    var context: Context = mock(Context::class.java)
    var firestore: FirebaseFirestore = mock(FirebaseFirestore::class.java)

    val view = Mockito.mock(AddNoteContract.View::class.java)
    val interactor = Mockito.mock(AddNoteContract.Interactor::class.java)
    lateinit var mPresenter: AddNotePresenter

    @Before
    fun initialize(){
        mPresenter = AddNotePresenter(view, interactor)
        doNothing().`when`(interactor).AddNote(titles, contents,context,firestore)
    }

    @Test
    fun testAddNote(){
        mPresenter.performAddNote(titles, contents, context, firestore)
        Mockito.verify(interactor, times(1)).AddNote(titles, contents, context, firestore)
    }
}