package com.example.hinotes.view

import android.content.Intent
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
import com.example.hinotes.core.editnote_activity.EditNoteContract
import com.example.hinotes.core.editnote_activity.EditNotePresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class EditNotesActivity : AppCompatActivity(), EditNoteContract.View {
    private lateinit var edtEditTitle: EditText
    private lateinit var edtEditContent: EditText
    private lateinit var progressBar: ProgressBar
    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseUser: FirebaseUser
    lateinit var mPresenter: EditNotePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notes)
        setSupportActionBar(findViewById(R.id.toolbarsEdit))

        edtEditTitle = findViewById(R.id.editTitle)
        edtEditContent = findViewById(R.id.editContent)
        progressBar = findViewById(R.id.progressBarEdit)

        edtEditTitle.setText(intent.getStringExtra("titles").toString())
        edtEditContent.setText(intent.getStringExtra("contents").toString())

        firestore = FirebaseFirestore.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        mPresenter = EditNotePresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_act_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_save -> {
                val valueTitle = edtEditTitle.text.toString()
                val valueContent = edtEditContent.text.toString()
                if (valueTitle.isEmpty() && valueContent.isEmpty()) {
                    Toast.makeText(this, "your note is empty!", Toast.LENGTH_LONG).show()
                    return false
                }
                progressBar.visibility = View.VISIBLE
                mPresenter.performUpdateNote(valueTitle, valueContent,this,firestore)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }
}