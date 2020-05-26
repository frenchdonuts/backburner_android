package io.onedonut.backburner.view_notes.interactors

import io.onedonut.backburner.view_notes.vm.VM
import io.reactivex.Observable

interface Interactors {
    fun meditations(): Observable<VM.Msg>
}