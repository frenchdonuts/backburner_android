package io.onedonut.backburner.dal

import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.onedonut.backburner.Database
import io.onedonut.backburner.model.Note
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NoteRepositoryImpl(db: Database):
    NoteRepository {
    private val queries = db.noteQueries

    override fun all(): Observable<List<Note>> {
        return queries
            .selectAll()
            .asObservable(Schedulers.io())
            .mapToList()
            .map { it.map { Note.from(it) }  }
    }

    override fun search(query: String): Single<List<Note>> {
        return queries
            .search("$query*")
            .asObservable()
            .mapToList()
            .map { it.map { Note.from(it) }  }
            .take(1)
            .singleOrError()
    }

    override fun create(text: String): Completable {
        return Completable.fromCallable {
            queries.insertNote(text)
        }
    }

    override fun edit(id: Long, newText: String): Completable {
        return Completable.fromCallable {
            queries.updateNote(newText, id)
        }
    }

    override fun delete(id: Long): Completable {
        return Completable.fromCallable {
            queries.deleteNote(id)
        }
    }

    companion object {
        private val TAG = "MeditationRepoImpl"
    }
}