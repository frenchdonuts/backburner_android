package io.onedonut.backburner.write_note.interactors

import io.onedonut.backburner.write_note.vm.VM
import io.reactivex.Single

interface Interactors {
    fun createMeditation(text: String): Single<VM.Msg>
}