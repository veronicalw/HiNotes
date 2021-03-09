package com.example.hinotes.core.main_activity

import com.example.hinotes.model.Notes
import com.google.firebase.firestore.DocumentReference

interface MainActivityContract {

    interface View{
        fun onProcessStart()
        fun onProcessEnd()
        fun showToast(message: String)
//        fun onNotesRead(notes: ArrayList<Notes>)
//        fun onNotesUpdate(notes: Notes)
//        fun onNotesDelete(notes: Notes)
    }

    interface Presenter{
        fun randomColour(): Int
        fun profileUpdate(name: String)
//        fun readNotes()
//        fun updateNotes(reference: DocumentReference, notes: Notes)
//        fun deleteNotes(reference: DocumentReference, notes: Notes)
    }

    interface Interactor{
        fun getrandomColour(): Int
        fun getprofileUpdate(name: String)
//        fun performReadNotes(reference: DocumentReference)
//        fun performUpdateNotes(reference: DocumentReference, notes: Notes)
//        fun performDeleteNotes(reference: DocumentReference)
    }

    interface onOperationListener{
        fun onShowToast(message: String)
        fun onStart()
        fun onEnd()
//        fun onRead(notes: ArrayList<Notes>)
//        fun onUpdate(notes: Notes)
//        fun onDelete(notes: Notes)
    }
}