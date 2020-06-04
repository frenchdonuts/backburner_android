package io.onedonut.backburner.view_notes.vm

import androidx.lifecycle.ViewModel
import arrow.optics.optics
import io.onedonut.backburner.base.VM
import io.onedonut.backburner.model.Note
import io.onedonut.backburner.view_notes.ui.UI

abstract class VM: VM<UI.Event, UI.State>, ViewModel() {
    //
    sealed class Msg {
        data class NotesLoaded(val notes: List<Note>) : Msg()
        data class NotesSearchResult(val results: List<Note>, val query: String) : Msg()
        object NoOp : Msg()
    }

    //
    @optics
    data class State(
        val uiState: UI.State = UI.State(),
        val notes: List<Note> = listOf()
    ) { companion object }

}