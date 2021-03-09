package com.example.hinotes.core.addnote_activity

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore


class AddNotePresenter: AddNoteContract.Presenter, AddNoteContract.onAddListener {

    private val mAddView: AddNoteContract.View? = null
    var mAddInteractor: AddNoteInteractor? = null

    override fun performAddNote(title: String, content: String, context: Context, firestore: FirebaseFirestore) {
        if (mAddInteractor?.AddNote(title,content,context,firestore)?.equals(null) == true){
            Toast.makeText(context,"Error", Toast.LENGTH_LONG).show()
        }
//       mAddInteractor?.AddNote(title, content, context, firestore)
    }

    override fun onSuccessListener(message: String) {
        mAddView?.onAddSuccess(message)
    }

    override fun onFailureListener(message: String) {
        mAddView?.onAddFailure(message)
    }
}