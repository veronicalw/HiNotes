package com.example.hinotes.core.main_activity

import com.example.hinotes.model.Notes

class MainActivityPresenter : MainActivityContract.Presenter,
    MainActivityContract.onOperationListener {
    private lateinit var mView: MainActivityContract.View
    private lateinit var mInteractor: MainActivityInteractor

    init {
        mInteractor = MainActivityInteractor(this)
    }

    override fun onShowToast(message: String) {
        mView.showToast(message)
    }

    override fun onStart() {
        mView.onProcessStart()
    }

    override fun onEnd() {
        mView.onProcessEnd()
    }

    override fun randomColour(): Int {
        return mInteractor.getrandomColour()
    }

    override fun profileUpdate(name: String) {
        mInteractor.getprofileUpdate(name)
    }
}