package io.onedonut.backburner.write_note.interactors

import io.onedonut.backburner.write_note.vm.VM
import io.onedonut.backburner.dal.NoteRepository
import io.reactivex.Single
import javax.inject.Inject

class InteractorsImpl @Inject constructor(val noteRepo: NoteRepository):
    Interactors {

    override fun createMeditation(text: String): Single<VM.Msg> {
        return noteRepo.create(text).toSingleDefault(VM.Msg.NoOp)
    }

}