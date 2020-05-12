package io.onedonut.backburner.meditations

import io.onedonut.backburner.repository.ItemRepository
import io.reactivex.Observable

class InteractorsImpl(val itemRepo: ItemRepository) :
    Interactors {

    override fun meditations(): Observable<VM.Msg> {
        return itemRepo.all()
            .map { VM.Msg.MeditationsLoaded(it) }
    }

    companion object {
        private val TAG = "meditations:IntersImpl"
    }
}