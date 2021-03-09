package com.example.hinotes.core.login_activity

import android.app.Activity
import android.content.Context


class LoginPresenter :
    LoginContract.Presenter, LoginContract.onLoginListener {
    private val mLoginView: LoginContract.View? = null
    private var mLoginInteractor: LoginInteractor? = null

    override fun login(activity: Activity?, email: String?, password: String?) {
        mLoginInteractor?.performFirebaseLogin(activity, email, password)
    }

    override fun loginAnonymously() {
        mLoginInteractor?.performFirebaseAnonymouslyLogin()
    }

    override fun logout(context: Context) {
        mLoginInteractor?.performLogout(context)
    }
    override fun onSuccess(message: String?) {
        mLoginView?.onLoginSuccess(message)
    }

    override fun onFailure(message: String?) {
        mLoginView?.onLoginFailure(message)
    }

    init {
        mLoginInteractor = LoginInteractor(this)
    }
}