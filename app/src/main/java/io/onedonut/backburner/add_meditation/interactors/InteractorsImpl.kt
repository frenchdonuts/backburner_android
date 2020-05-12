package io.onedonut.backburner.add_meditation.interactors

import io.onedonut.backburner.add_meditation.vm.VM
import io.onedonut.backburner.repository.ItemRepository
import io.reactivex.Single

class InteractorsImpl(val itemRepo: ItemRepository):
    Interactors {

    override fun createMeditation(text: String): Single<VM.Msg> {
        return itemRepo.create(text).toSingleDefault(VM.Msg.NoOp)
    }

}