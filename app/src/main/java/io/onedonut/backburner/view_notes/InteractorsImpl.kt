package io.onedonut.backburner.view_notes

import io.onedonut.backburner.repository.NoteRepository
import io.reactivex.Observable

class InteractorsImpl(val noteRepo: NoteRepository) :
    Interactors {

    override fun meditations(): Observable<VM.Msg> {
        return noteRepo.all()
            .map { VM.Msg.MeditationsLoaded(it) }
    }

    companion object {
        private val TAG = "meditations:IntersImpl"
    }
}