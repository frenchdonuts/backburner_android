package io.onedonut.backburner.view_notes.interactors

import io.onedonut.backburner.dal.NoteRepository
import io.onedonut.backburner.view_notes.vm.VM
import io.reactivex.Observable
import javax.inject.Inject

class InteractorsImpl @Inject constructor(val noteRepo: NoteRepository) :
    Interactors {

    override fun meditations(): Observable<VM.Msg> {
        return noteRepo.all()
            .map { VM.Msg.NotesLoaded(it) }
    }

    companion object {
        private val TAG = "meditations:IntersImpl"
    }
}