package com.example.hinotes.core.editnote_activity

import android.app.Activity
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

class EditNotePresenter(editView: EditNoteContract.View): EditNoteContract.Presenter, EditNoteContract.onAddListener {
    private val mEditView: EditNoteContract.View
    private val mEditInteractor: EditNoteInteractor

    init {
        this.mEditView = editView
        mEditInteractor = EditNoteInteractor(this)
    }
    override fun performUpdateNote(
        title: String,
        content: String,
        context: Activity,
        firestore: FirebaseFirestore
    ) {
        mEditInteractor.UpdateNote(title, content, context, firestore)
    }
}