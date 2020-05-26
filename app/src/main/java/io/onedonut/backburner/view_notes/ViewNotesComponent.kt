package io.onedonut.backburner.view_notes

import dagger.Subcomponent
import io.onedonut.backburner.view_notes.vm.VM
import javax.inject.Provider

@Subcomponent(modules = [ViewModelModule::class, InteractorsModule::class])
interface ViewNotesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ViewNotesComponent
    }

    fun vm(): Provider<VM>

}