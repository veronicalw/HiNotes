package com.example.hinotes.core.login_activity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.hinotes.MainActivity
import com.example.hinotes.SplashScreenActivity
import com.example.hinotes.core.login_activity.LoginContract.Intractor
import com.example.hinotes.core.login_activity.LoginContract.onLoginListener
import com.example.hinotes.view.RegisterActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class LoginInteractor(onLoginListener:LoginContract.onLoginListener):LoginContract.Intractor {
    private var mOnLoginListener : LoginContract.onLoginListener

    init{
        this.mOnLoginListener = onLoginListener
    }

    override fun performFirebaseLogin(activity: Activity?, email: String?, password: String?) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mOnLoginListener.onSuccess(task.result.toString())
                } else {
                    mOnLoginListener.onFailure(task.exception.toString())
                }
            }
    }

    override fun performFirebaseAnonymouslyLogin() {
        FirebaseAuth.getInstance()
            .signInAnonymously().addOnSuccessListener(object :
                OnSuccessListener<AuthResult> {
                override fun onSuccess(p0: AuthResult?) {
                    mOnLoginListener.onSuccess("Successfully logged as anonymous")
                }
            })
            .addOnFailureListener{
                Log.d("ANONYMOUS_Process","Error in: " + it.localizedMessage.toString())
                mOnLoginListener.onFailure("Authentication Failed")
            }
    }

    override fun performLogout(context: Context) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, SplashScreenActivity::class.java)
        context.startActivity(intent)
    }

}