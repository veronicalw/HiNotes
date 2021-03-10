package com.example.hinotes.core.main_activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hinotes.R
import com.example.hinotes.model.Notes
import com.example.hinotes.view.DetailNotesActivity
import com.example.hinotes.view.EditNotesActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlin.random.Random

class MainActivityInteractor(onOperationListener: MainActivityContract.onOperationListener) : MainActivityContract.Interactor {

    private var mListener: MainActivityContract.onOperationListener
    private lateinit var notesAdapter: FirestoreRecyclerAdapter<Notes, NotesViewHolder>

    init {
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

    override fun performReadNotes(context: Context, recyclerView: RecyclerView) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!
        var query: Query = FirebaseFirestore.getInstance().collection("hinotes").document(firebaseUser.uid).collection("tb_notes").orderBy("titles", Query.Direction.DESCENDING)
        var notes: FirestoreRecyclerOptions<Notes> = FirestoreRecyclerOptions.Builder<Notes>().setQuery(query, Notes::class.java).build()
        notesAdapter = object : FirestoreRecyclerAdapter<Notes, NotesViewHolder>(notes) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
                val View = LayoutInflater.from(parent.context).inflate(R.layout.note_item_test, parent, false)
                return NotesViewHolder(View)
            }
            override fun onBindViewHolder(viewHolder: NotesViewHolder, position: Int, notes: Notes) {
                viewHolder.txtTitle.setText(notes.titles)
                viewHolder.txtContent.setText(notes.contents)
                viewHolder.cvNote.setCardBackgroundColor(viewHolder.view.resources.getColor(getrandomColour(), null))
                val context = viewHolder.txtTitle.context
                var docId = notesAdapter.snapshots.getSnapshot(position).id
                viewHolder.view.setOnClickListener {
                    val intent = Intent(context, DetailNotesActivity::class.java)
                    intent.putExtra("titles", notes.titles)
                    intent.putExtra("contents", notes.contents)
                    intent.putExtra("id", docId)
                    context.startActivity(intent)
                }
                val imageMenu: ImageView = viewHolder.view.findViewById(R.id.menu)
                imageMenu.setOnClickListener {
                    val popupMenu = PopupMenu(it.context, it)
                    var docId = notesAdapter.snapshots.getSnapshot(position).id
                    popupMenu.menu.add("Edit").setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                        override fun onMenuItemClick(p0: MenuItem?): Boolean {
                            val intent = Intent(context, EditNotesActivity::class.java)
                            intent.putExtra("titles", notes.titles)
                            intent.putExtra("contents", notes.contents)
                            intent.putExtra("id", docId)
                            context.startActivity(intent)
                            return false
                        }
                    })
                    popupMenu.menu.add("Delete").setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                        override fun onMenuItemClick(p0: MenuItem?): Boolean {
                            val documentReferences: DocumentReference =
                                FirebaseFirestore.getInstance().collection("hinotes").document(firebaseUser.uid).collection("tb_notes").document(docId)
                            documentReferences
                                .delete()
                                .addOnSuccessListener { Log.d("DELETE NOTE", "DocumentSnapshot successfully deleted!")
                                    Toast.makeText(context,"Successfully Deleted!", Toast.LENGTH_SHORT).show()}
                                .addOnFailureListener { e -> Log.w("DELETE NOTE", "Error deleting document", e)
                                    Toast.makeText(context,"Error Delete!", Toast.LENGTH_SHORT).show() }
                            return false
                        }

                    })
                    popupMenu.show()
                }
            }
        }
        recyclerView.adapter = notesAdapter
    }

    override fun onStart(){
        notesAdapter!!.startListening()
    }
    override fun onStop(){
        if(notesAdapter != null){
            notesAdapter.stopListening()
        }
    }

}