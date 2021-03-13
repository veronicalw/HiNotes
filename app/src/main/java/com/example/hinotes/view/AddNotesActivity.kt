package com.example.hinotes.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hinotes.R
import com.example.hinotes.core.addnote_activity.AddNoteContract
import com.example.hinotes.core.addnote_activity.AddNoteInteractor
import com.example.hinotes.core.addnote_activity.AddNotePresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AddNotesActivity : AppCompatActivity(), AddNoteContract.View, AddNoteContract.onAddListener {
    private lateinit var edtTitle: EditText
    private lateinit var edtContent: EditText
    lateinit var firestore: FirebaseFirestore
    private lateinit var progressBar: ProgressBar
    lateinit var firebaseUser: FirebaseUser
    lateinit var mPresenter: AddNotePresenter
    val mInteractor = AddNoteInteractor(this)

    init {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        setSupportActionBar(findViewById(R.id.toolbars))

        edtTitle = findViewById(R.id.edtNoteTitle)
        edtContent = findViewById(R.id.edtNoteContent)
        progressBar = findViewById(R.id.progressBar)
        firestore = FirebaseFirestore.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_act_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.nav_save -> {
                mPresenter = AddNotePresenter(this, mInteractor)
                val stringTitle = edtTitle.text.toString()
                val stringContent = edtContent.text.toString()
                if (stringTitle.isEmpty() && stringContent.isEmpty()) {
                    Toast.makeText(this, "your note shouldn't be empty!", Toast.LENGTH_LONG).show()
                    return false
                }
                progressBar.visibility = View.VISIBLE
                mPresenter.performAddNote(stringTitle, stringContent,this@AddNotesActivity, firestore)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onAddSuccess(message: String?) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        finish()
    }
    override fun onAddFailure(message: String?) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSuccessListener(message: String) {
//        TODO("Not yet implemented")
    }

    override fun onFailureListener(message: String) {
//        TODO("Not yet implemented")
    }
}