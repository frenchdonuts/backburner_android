package io.onedonut.backburner.view_notes.ui

import arrow.optics.optics

interface UI : io.onedonut.backburner.base.UI<UI.Event, UI.State> {

    sealed class Event {
        data class SearchTextChanged(val query: CharSequence) : Event()
        object UiInitialized : Event()
        object UiRecreated : Event()
    }

    @optics
    data class State(
        val items: List<Item> = listOf(),
        val emptySearchViewIsVisible: Boolean = false
    ) { companion object }

    data class Item(
        val id: String,
        val text: String
    )
}