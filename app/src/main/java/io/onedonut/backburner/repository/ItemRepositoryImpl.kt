package io.onedonut.backburner.repository

import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.onedonut.backburner.Database
import io.onedonut.backburner.model.Item
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ItemRepositoryImpl(val db: Database):
    ItemRepository {
    private val queries = db.itemQueries

    override fun all(): Observable<List<Item>> {
        return queries
            .selectAll()
            .asObservable(Schedulers.io())
            .mapToList()
            .map { it.map { Item.from(it) }  }
    }

    override fun create(text: String): Completable {
        return Completable.fromCallable {
            queries.insertItem(text)
        }
    }

    companion object {
        private val TAG = "MeditationRepoImpl"
    }
}