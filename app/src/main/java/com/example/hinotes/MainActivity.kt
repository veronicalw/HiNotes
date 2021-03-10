package com.example.hinotes

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.*
import com.example.hinotes.core.main_activity.MainActivityContract
import com.example.hinotes.core.main_activity.MainActivityPresenter
import com.example.hinotes.view.AddNotesActivity
import com.example.hinotes.view.RegisterActivity
import com.example.hinotes.view.SettingsActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainActivityContract.View {
    //Objects
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var menuDrawer: ImageView
    private lateinit var addNotes: ImageView
    private lateinit var layoutDashboard: LinearLayout
    private lateinit var rvStore: RecyclerView
    private lateinit var userNames: TextView
    private lateinit var userEmails: TextView

    //Firebase
    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var mListener: MainActivityPresenter

    //Custom Navigation Drawer
    private var endScale: Float = 1.8f
    lateinit var headerView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById(R.id.layoutDrawer)
        navigationView = findViewById(R.id.navigation_view)
        menuDrawer = findViewById(R.id.imgDrawerMenu)
        addNotes = findViewById(R.id.imgAddNotes)
        layoutDashboard = findViewById(R.id.linearMainActivity)
        rvStore = findViewById(R.id.rcViewStore)

        //Calling Firestore user
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
        mListener = MainActivityPresenter(this)

        rvStore.layoutManager = LinearLayoutManager(this)
        rvStore.layoutManager = GridLayoutManager(this, 2)
        rvStore.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mListener.readNotes(this, rvStore)
        //Set Navigation Drawer Header with Textview to Display User's Name and Email
        headerView = navigationView.getHeaderView(0)
        userNames = headerView.findViewById(R.id.userNames)
        userEmails = headerView.findViewById(R.id.userEmails)
//        userNames.setText(firebaseUser.displayName)
        userEmails.setText(firebaseUser.email)

        //To set if the logged user is anonymous
        if (firebaseUser.isAnonymous) {
            userNames.setText("Temporary Account")
            userEmails.visibility = View.GONE
        } else {
            mListener.profileUpdate(userNames.toString())
//            userNames.setText(firebaseUser.displayName)
            userEmails.setText(firebaseUser.email)
        }

        addNotes.setOnClickListener {
            val addNotesAct = Intent(this, AddNotesActivity::class.java)
            startActivity(addNotesAct)
        }
        navigationDrawerSetting()
    }

    private fun checkingUser() {
        if (firebaseUser.isAnonymous == true) {
            displayAlert()
        } else {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, SplashScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun displayAlert() {
        val alertDialog = AlertDialog.Builder(this).setTitle("Are you sure?")
            .setMessage("You are currently using temporary account, logging out will delete all of your data")
            .setPositiveButton("Let's Sync Your Note", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
            .setNegativeButton("Log Out", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    firebaseUser.delete().addOnSuccessListener(object : OnSuccessListener<Void> {
                        override fun onSuccess(it: Void?) {
                            val intent = Intent(this@MainActivity, SplashScreenActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
                }
            })
        alertDialog.show()
    }

    private fun navigationDrawerSetting() {
        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setNavigationItemSelectedListener {
            return@setNavigationItemSelectedListener when (it.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_logout -> {
                    checkingUser()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_profile -> {
                    if (firebaseUser.isAnonymous) {
                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "You are connected already", Toast.LENGTH_LONG).show()
                    }
                    true
                }
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
        navigationView.setCheckedItem(R.id.nav_home)
        menuDrawer.setOnClickListener {
            if (drawerLayout.isDrawerVisible(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
            else drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationDrawerAnimation()
    }

    private fun navigationDrawerAnimation() {
        drawerLayout.setScrimColor(resources.getColor(R.color.tan))
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(@NonNull drawerView: View, slideOffSet: Float) {
                val diffScaleOffSet = slideOffSet * (1 - endScale)
                val offSetScale = 1 - diffScaleOffSet
                layoutDashboard.setScaleX(offSetScale)
                layoutDashboard.setScaleY(offSetScale)
                //Translate View to scale width
                val xOffSet = drawerView.getWidth() * slideOffSet
                val xOffSetDiff = layoutDashboard.getWidth() * diffScaleOffSet / 2
                val xTransition = xOffSet - xOffSetDiff
                layoutDashboard.setTranslationX(xTransition)
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        mListener.startListening()
    }

    override fun onStop() {
        super.onStop()
        mListener.stopListening()
    }
}