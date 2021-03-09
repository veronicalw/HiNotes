package com.example.hinotes

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hinotes.core.login_activity.LoginContract
import com.example.hinotes.core.login_activity.LoginPresenter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity(), LoginContract.View {
    lateinit var firebaseAuth: FirebaseAuth
    val screenTimeOut = 4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        firebaseAuth = FirebaseAuth.getInstance()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        Handler().postDelayed(object : Runnable {
            public override fun run() {
                if (firebaseAuth.currentUser != null) {
                    val Home = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(Home)
                    finish()
                } else {
                    firebaseAuth.signInAnonymously().addOnSuccessListener(object :
                            OnSuccessListener<AuthResult> {
                        override fun onSuccess(p0: AuthResult?) {
                            onLoginSuccess("Logged in Anonymously")
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            finish()
                        }
                    })
                            .addOnFailureListener{
                                onLoginFailure("Error in : " + it.localizedMessage.toString())
                                finish()
                            }
                }
            }
        }, screenTimeOut.toLong())
    }

    override fun onLoginSuccess(message: String?) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    override fun onLoginFailure(message: String?) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
}