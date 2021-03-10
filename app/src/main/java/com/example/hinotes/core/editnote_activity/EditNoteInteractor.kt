package com.example.hinotes.core.editnote_activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.hinotes.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class EditNoteInteractor(onAddListener: EditNoteContract.onAddListener) :
    EditNoteContract.Interactor {
    private var mOnAddListener: EditNoteContract.onAddListener

    init {
        this.mOnAddListener = onAddListener
    }

    override fun UpdateNote(
        title: String,
        content: String,
        context: Activity,
        firestore: FirebaseFirestore
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val documentReference: DocumentReference =
            firestore.collection("hinotes").document(firebaseUser.uid).collection("tb_notes").document(context.intent.getStringExtra("id").toString())
        var note: HashMap<String, Any> = HashMap<String, Any>()
        note.put("titles", title)
        note.put("contents", content)
        documentReference.update(note).addOnSuccessListener {
            Toast.makeText(context,"Successfully Saved!", Toast.LENGTH_LONG).show()
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(context,"Error save!", Toast.LENGTH_LONG).show()
        }
    }

}