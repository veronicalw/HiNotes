package com.example.hinotes.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hinotes.R
import com.example.hinotes.core.main_activity.MainActivityPresenter
import com.example.hinotes.view.DetailNotesActivity


class NotesAdapter(val title: List<String>, val content: List<String>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    lateinit var mPresenter: MainActivityPresenter

    init {
        title
        content
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item_test, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mPresenter = MainActivityPresenter()
        holder.txtTitle.text = title.get(position)
        holder.txtContent.text = content.get(position)
        holder.cvNote.setCardBackgroundColor(holder.view.resources.getColor(mPresenter.randomColour(), null))
        val context = holder.txtTitle.context
        holder.view.setOnClickListener {
            val intent = Intent(context, DetailNotesActivity::class.java)
            intent.putExtra("titles", title.get(position))
            intent.putExtra("contents", content.get(position))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return title.size
    }

    class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val txtTitle: TextView = itemView.findViewById(R.id.titleNote)
        internal val txtContent: TextView = itemView.findViewById(R.id.txtNoteContent)
        internal val cvNote: CardView = itemView.findViewById(R.id.cvView)
        internal val view: View = itemView
    }
}