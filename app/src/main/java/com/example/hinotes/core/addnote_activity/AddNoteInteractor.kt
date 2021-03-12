package com.example.hinotes.core.addnote_activity

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.hinotes.MainActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class AddNoteInteractor(onAddListener: AddNoteContract.onAddListener) : AddNoteContract.Interactor {
    private var mOnAddListener: AddNoteContract.onAddListener

    init {
        this.mOnAddListener = onAddListener
    }

    override fun AddNote(title: String, content: String, context: Context, firestore: FirebaseFirestore) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val documentReference: DocumentReference = firestore.collection("hinotes").document(firebaseUser.uid).collection("tb_notes").document()
        var note: HashMap<String, Any> = HashMap<String, Any>()
        note.put("titles", title)
        note.put("contents", content)
        documentReference.set(note).addOnSuccessListener {
            Toast.makeText(context, "Successfully saved! ", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(context, "Error save, caused by : " + it.toString(), Toast.LENGTH_LONG).show()
        }
        Toast.makeText(context, "Calling Add NOTE ", Toast.LENGTH_LONG).show()
    }

}