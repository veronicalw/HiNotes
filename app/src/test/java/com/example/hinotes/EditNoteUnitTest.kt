package com.example.hinotes

import android.app.Activity
import android.content.Context
import com.example.hinotes.core.addnote_activity.AddNoteContract
import com.example.hinotes.core.addnote_activity.AddNotePresenter
import com.example.hinotes.core.editnote_activity.EditNoteContract
import com.example.hinotes.core.editnote_activity.EditNotePresenter
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EditNoteUnitTest {
    val titles = "note1"
    val contents = "contents1"
    var context: Activity = Mockito.mock(Activity::class.java)
    var firestore: FirebaseFirestore = Mockito.mock(FirebaseFirestore::class.java)

    val view = Mockito.mock(EditNoteContract.View::class.java)
    val interactor = Mockito.mock(EditNoteContract.Interactor::class.java)
    lateinit var mPresenter: EditNotePresenter

    @Before
    fun initialize(){
        mPresenter = EditNotePresenter(view, interactor)
        Mockito.doNothing().`when`(interactor).UpdateNote(titles, contents,context,firestore)
    }

    @Test
    fun testEditNote(){
        mPresenter.performUpdateNote(titles, contents, context, firestore)
        Mockito.verify(interactor, times(1)).UpdateNote(titles, contents, context, firestore)
    }

}