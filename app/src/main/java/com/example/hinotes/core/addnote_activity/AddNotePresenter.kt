package com.example.hinotes.core.addnote_activity

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore


class AddNotePresenter(addView: AddNoteContract.View): AddNoteContract.Presenter, AddNoteContract.onAddListener {

    private val mAddView: AddNoteContract.View
    private val mAddInteractor: AddNoteInteractor

    init {
        this.mAddView = addView
        mAddInteractor = AddNoteInteractor(this)
    }
    override fun performAddNote(title: String, content: String, context: Context, firestore: FirebaseFirestore) {
       mAddInteractor.AddNote(title, content, context, firestore)
    }

    override fun onSuccessListener(message: String) {
        mAddView?.onAddSuccess(message)
    }

    override fun onFailureListener(message: String) {
        mAddView?.onAddFailure(message)
    }
}