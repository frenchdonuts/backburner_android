package io.onedonut.backburner

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arrow.syntax.function.memoize
import io.onedonut.backburner.write_note.interactors.InteractorsImpl
import io.onedonut.backburner.write_note.vm.WriteNoteViewModel
import io.onedonut.backburner.write_note.vm.VM
import io.onedonut.backburner.view_notes.ViewNotesViewModel
import io.onedonut.backburner.repository.NoteRepositoryImpl
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == io.onedonut.backburner.view_notes.VM::class.java)
            return ViewNotesViewModel(
                io.onedonut.backburner.view_notes.InteractorsImpl(
                    NoteRepositoryImpl(
                        App.db
                    )
                )
            ) as T
        else if (modelClass == VM::class.java)
            return WriteNoteViewModel(
                InteractorsImpl(
                    NoteRepositoryImpl(
                        App.db
                    )
                )
            ) as T

        throw IllegalArgumentException("unknown model class: $modelClass")
    }

    companion object {
        val instance = { context: Context ->
            ViewModelFactory(context.applicationContext)
        }.memoize()
    }
}