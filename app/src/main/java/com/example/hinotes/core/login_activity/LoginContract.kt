package com.example.hinotes.core.login_activity

import android.app.Activity
import android.content.Context


interface LoginContract {

    interface View {
        fun onLoginSuccess(message: String?)
        fun onLoginFailure(message: String?)
    }

    interface Presenter {
        fun login(activity: Activity?, email: String?, password: String?)
        fun loginAnonymously()
        fun logout(context: Context)
    }

    interface Intractor {
        fun performFirebaseLogin(activity: Activity?, email: String?, password: String?)
        fun performFirebaseAnonymouslyLogin()
        fun performLogout(context: Context)
    }

    interface onLoginListener {
        fun onSuccess(message: String?)
        fun onFailure(message: String?)
    }
}