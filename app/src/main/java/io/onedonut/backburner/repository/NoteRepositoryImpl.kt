package io.onedonut.backburner.repository

import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.onedonut.backburner.Database
import io.onedonut.backburner.model.Note
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NoteRepositoryImpl(val db: Database):
    NoteRepository {
    private val queries = db.noteQueries

    override fun all(): Observable<List<Note>> {
        return queries
            .selectAll()
            .asObservable(Schedulers.io())
            .mapToList()
            .map { it.map { Note.from(it) }  }
    }

    override fun create(text: String): Completable {
        return Completable.fromCallable {
            queries.insertNote(text)
        }
    }

    companion object {
        private val TAG = "MeditationRepoImpl"
    }
}