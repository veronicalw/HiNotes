package com.example.hinotes.core.main_activity

import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hinotes.R

class NotesViewHolder (@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal val txtTitle: TextView = itemView.findViewById(R.id.titleNote)
    internal val txtContent: TextView = itemView.findViewById(R.id.txtNoteContent)
    internal val cvNote: CardView = itemView.findViewById(R.id.cvView)
    internal val view: View = itemView
}