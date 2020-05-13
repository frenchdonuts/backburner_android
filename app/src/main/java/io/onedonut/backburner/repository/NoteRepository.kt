package io.onedonut.backburner.repository

import io.onedonut.backburner.model.Note
import io.reactivex.Completable
import io.reactivex.Observable

interface NoteRepository {
    fun all(): Observable<List<Note>>
    fun create(text: String): Completable
}