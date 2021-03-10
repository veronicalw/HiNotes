package com.example.hinotes.core.main_activity

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

interface MainActivityContract {

    interface View{
    }

    interface Presenter{
        fun randomColour(): Int
        fun profileUpdate(name: String)
        fun readNotes(context: Context, recyclerView: RecyclerView)
        fun startListening()
        fun stopListening()
    }

    interface Interactor{
        fun getrandomColour(): Int
        fun getprofileUpdate(name: String)
        fun performReadNotes(context: Context, recyclerView: RecyclerView)
        fun onStart()
        fun onStop()
    }

    interface onOperationListener{
    }
}