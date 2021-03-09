package com.example.hinotes.core.addnote_activity

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

interface AddNoteContract {
    interface View{
        fun onAddSuccess(message: String?)
        fun onAddFailure(message: String?)
    }
    interface Interactor{
        fun AddNote(title: String, content: String, context: Context, firestore: FirebaseFirestore)
    }
    interface Presenter{
        fun performAddNote(title: String, content: String, context: Context, firestore: FirebaseFirestore)
    }
    interface onAddListener{
        fun onSuccessListener(message: String)
        fun onFailureListener(message: String)
    }
}