package io.onedonut.backburner.notes

import androidx.lifecycle.ViewModel
import io.onedonut.backburner.base.VM
import io.onedonut.backburner.model.Note

abstract class VM: VM<UI.Event, UI.State>, ViewModel() {
    //
    sealed class Msg {
        data class MeditationsLoaded(val notes: List<Note>) : Msg()
        object NoOp : Msg()
    }

    //
    data class State(
        val uiState: UI.State = UI.State(),
        val notes: List<Note> = listOf()
    )

}