package io.onedonut.backburner.notes

import io.reactivex.Observable

interface Interactors {
    fun meditations(): Observable<VM.Msg>
}