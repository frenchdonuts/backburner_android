package io.onedonut.backburner.repository

import io.onedonut.backburner.model.Item
import io.reactivex.Completable
import io.reactivex.Observable

interface ItemRepository {
    fun all(): Observable<List<Item>>
    fun create(text: String): Completable
}