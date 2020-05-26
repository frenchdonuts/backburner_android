package io.onedonut.backburner.write_note

import dagger.Module
import dagger.Provides
import io.onedonut.backburner.write_note.interactors.Interactors
import io.onedonut.backburner.write_note.vm.VM
import io.onedonut.backburner.write_note.vm.WriteNoteViewModel

@Module
class ViewModelModule {

    @Provides
    fun vm(interactors: Interactors): VM {
        return WriteNoteViewModel(interactors)
    }

}