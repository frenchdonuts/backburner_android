package io.onedonut.backburner.view_notes

import dagger.Module
import dagger.Provides
import io.onedonut.backburner.view_notes.interactors.Interactors
import io.onedonut.backburner.view_notes.vm.VM
import io.onedonut.backburner.view_notes.vm.ViewNotesViewModel


@Module
class ViewModelModule {

    @Provides
    fun viewNotesVM(interactors: Interactors): VM {
        return ViewNotesViewModel(interactors)
    }

}
