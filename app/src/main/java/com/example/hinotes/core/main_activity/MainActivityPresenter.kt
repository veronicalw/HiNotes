package com.example.hinotes.core.main_activity

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.hinotes.core.addnote_activity.AddNoteContract
import com.example.hinotes.model.Notes
import com.google.firebase.firestore.DocumentReference

class MainActivityPresenter(val addView: MainActivityContract.View, val mInteractor: MainActivityContract.Interactor): MainActivityContract.Presenter,
    MainActivityContract.onOperationListener {
//    private var mView: MainActivityContract.View
//    val mInteractor: MainActivityInteractor = MainActivityInteractor(this)

//    init {
//        mView = addView
//        mInteractor = MainActivityInteractor(this)
//    }

    override fun randomColour(): Int {
        return mInteractor.getrandomColour()
        return randomColour()
    }

    override fun profileUpdate(name: String) {
        mInteractor.getprofileUpdate(name)
    }

    override fun readNotes(context: Context, recyclerView: RecyclerView) {
        mInteractor.performReadNotes(context, recyclerView)
    }

    override fun startListening() {
        mInteractor.onStart()
    }

    override fun stopListening() {
        mInteractor.onStop()
    }
}