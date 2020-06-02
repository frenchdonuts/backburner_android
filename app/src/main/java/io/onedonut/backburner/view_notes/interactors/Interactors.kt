package io.onedonut.backburner.view_notes.interactors

import io.onedonut.backburner.view_notes.vm.VM
import io.reactivex.Observable
import io.reactivex.Single

interface Interactors {
    fun meditations(): Observable<VM.Msg>
    fun searchNotes(query: CharSequence): Single<VM.Msg>
}