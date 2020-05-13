package io.onedonut.backburner.model


data class Note(val id: String,
                val text: String) {
    companion object {
        fun from(note: io.onedonut.backburner.Note): Note {
            return Note(
                note.id.toString(),
                note.text
            )
        }
    }
}