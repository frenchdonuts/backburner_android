package io.onedonut.backburner.view_notes

import io.reactivex.Observable

interface Interactors {
    fun meditations(): Observable<VM.Msg>
}