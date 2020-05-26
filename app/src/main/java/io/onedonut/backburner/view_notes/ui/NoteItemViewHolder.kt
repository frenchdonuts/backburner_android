package io.onedonut.backburner.view_notes.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.onedonut.backburner.databinding.NoteItemBinding
import io.onedonut.backburner.view_notes.ui.UI

class NoteItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val meditationText: TextView get() = binding.meditationText
    private val binding: NoteItemBinding = NoteItemBinding.bind(itemView)

    fun bind(item: UI.Item) {
        meditationText.text = item.quote
    }
}