package io.onedonut.backburner.meditations

import androidx.lifecycle.ViewModel
import io.onedonut.backburner.base.VM
import io.onedonut.backburner.model.Item

abstract class VM: VM<UI.Event, UI.State>, ViewModel() {
    //
    sealed class Msg {
        data class MeditationsLoaded(val items: List<Item>) : Msg()
        object NoOp : Msg()
    }

    //
    data class State(
        val uiState: UI.State = UI.State(),
        val items: List<Item> = listOf()
    )

}