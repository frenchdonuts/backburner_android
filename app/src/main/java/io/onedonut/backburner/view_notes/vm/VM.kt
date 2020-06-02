package io.onedonut.backburner.view_notes.vm

import androidx.lifecycle.ViewModel
import io.onedonut.backburner.base.VM
import io.onedonut.backburner.model.Note
import io.onedonut.backburner.view_notes.ui.UI

abstract class VM: VM<UI.Event, UI.State>, ViewModel() {
    //
    sealed class Msg {
        data class NotesLoaded(val notes: List<Note>) : Msg()
        object NoOp : Msg()
    }

    //
    data class State(
        val uiState: UI.State = UI.State(),
        val notes: List<Note> = listOf()
    )

}