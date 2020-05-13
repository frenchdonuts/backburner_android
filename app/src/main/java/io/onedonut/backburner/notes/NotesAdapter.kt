package io.onedonut.backburner.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.onedonut.backburner.R

class NotesAdapter : ListAdapter<UI.Item, NoteItemViewHolder>(
    DiffCallback
) {

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteItemViewHolder(view)
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<UI.Item>() {
            override fun areItemsTheSame(oldItem: UI.Item, newItem: UI.Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UI.Item, newItem: UI.Item): Boolean {
                return oldItem == newItem
            }

        }
    }
}