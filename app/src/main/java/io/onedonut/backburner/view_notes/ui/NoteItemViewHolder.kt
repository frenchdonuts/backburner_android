package io.onedonut.backburner.view_notes.ui

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.onedonut.backburner.databinding.NoteItemBinding

class NoteItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val noteText: TextView get() = binding.noteText
    private val binding: NoteItemBinding = NoteItemBinding.bind(itemView)

    fun bind(item: UI.Item) {
        noteText.text = item.text
        noteText.setOnClickListener {
            Log.d("item:${item.text}", "clicked!")
        }
    }
}