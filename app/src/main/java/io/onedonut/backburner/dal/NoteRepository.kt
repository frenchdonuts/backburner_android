package io.onedonut.backburner.dal

import io.onedonut.backburner.model.Note
import io.reactivex.Completable
import io.reactivex.Observable

interface NoteRepository {
    fun all(): Observable<List<Note>>
    fun create(text: String): Completable
    fun edit(id: Long, newText: String): Completable
    fun delete(id: Long): Completable
}