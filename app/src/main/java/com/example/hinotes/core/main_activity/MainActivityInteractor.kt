package com.example.hinotes.core.main_activity

import android.view.View
import com.example.hinotes.R
import com.example.hinotes.core.login_activity.LoginContract
import com.example.hinotes.core.main_activity.MainActivityContract.onOperationListener
import com.example.hinotes.model.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class MainActivityInteractor(onOperationListener: MainActivityContract.onOperationListener): MainActivityContract.Interactor {

    private var mListener: MainActivityContract.onOperationListener

    init{
        this.mListener = onOperationListener
    }

    override fun getrandomColour(): Int {
        val colours = arrayListOf<Int>()
        colours.addAll(listOf(
                R.color.cornsilk,
                R.color.blanchedalmond,
                R.color.bisque,
                R.color.navajowhite,
                R.color.wheat,
                R.color.burlywood,
                R.color.tan
        ))
        var randomColour = Random
        val number = randomColour.nextInt(colours.size)
        return colours.get(number)
    }

    override fun getprofileUpdate(name: String) {
        val userRequest = UserProfileChangeRequest.Builder().apply {
            displayName = name
        }.build()
        FirebaseAuth.getInstance().currentUser.updateProfile(userRequest)
    }

}