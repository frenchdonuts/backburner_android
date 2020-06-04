package io.onedonut.backburner.view_notes.interactors

import android.util.Log
import io.onedonut.backburner.dal.NoteRepository
import io.onedonut.backburner.view_notes.vm.VM
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class InteractorsImpl @Inject constructor(val noteRepo: NoteRepository) :
    Interactors {

    override fun meditations(): Observable<VM.Msg> {
        return noteRepo.all()
            .map { VM.Msg.NotesLoaded(it) }
    }

    override fun searchNotes(query: String): Single<VM.Msg> {
        return noteRepo.search(query)
            .doOnSuccess { Log.d(TAG, "$it") }
            .map { VM.Msg.NotesSearchResult(it, query) }
    }

    companion object {
        private val TAG = "meditations:IntersImpl"
    }
}