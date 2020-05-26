package io.onedonut.backburner.write_note

import dagger.Subcomponent
import io.onedonut.backburner.write_note.vm.VM
import javax.inject.Provider

@Subcomponent(modules = [ViewModelModule::class, InteractorsModule::class])
interface WriteNoteComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WriteNoteComponent
    }

    fun vm(): Provider<VM>
}