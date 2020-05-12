package io.onedonut.backburner.add_meditation.vm

import androidx.lifecycle.ViewModel
import io.onedonut.backburner.add_meditation.ui.UI
import io.onedonut.backburner.base.VM

abstract class VM: VM<UI.Event, UI.State>, ViewModel() {
    sealed class Msg {
        object NoOp : Msg()
    }
    data class State(val uiState: UI.State = UI.State())
}