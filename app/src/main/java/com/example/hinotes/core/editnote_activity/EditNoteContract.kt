package com.example.hinotes.core.editnote_activity

import android.app.Activity
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

interface EditNoteContract {
    interface View {
        fun showToast(message: String)
    }

    interface Interactor {
        fun UpdateNote(
            title: String, content: String,
            context: Activity, firestore: FirebaseFirestore
        )
    }

    interface Presenter {
        fun performUpdateNote(
            title: String,
            content: String,
            context: Activity,
            firestore: FirebaseFirestore
        )
    }

    interface onAddListener {
    }
}