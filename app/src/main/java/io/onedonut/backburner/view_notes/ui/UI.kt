package io.onedonut.backburner.view_notes.ui

interface UI : io.onedonut.backburner.base.UI<UI.Event, UI.State> {

    sealed class Event {
        object UiInitialized : Event()
        object UiRecreated : Event()
    }

    data class State(val items: List<Item> = listOf())

    data class Item(
        val id: String,
        val text: String
    )
}