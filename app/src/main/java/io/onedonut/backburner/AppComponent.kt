package io.onedonut.backburner

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.onedonut.backburner.dal.DataAccessLayerModule
import io.onedonut.backburner.view_notes.ViewNotesComponent
import io.onedonut.backburner.write_note.WriteNoteComponent

@Component(modules = [AppSubcomponents::class, DataAccessLayerModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context,
                   @BindsInstance db: Database): AppComponent
    }

    fun viewNotesComponent(): ViewNotesComponent.Factory
    fun writeNoteComponent(): WriteNoteComponent.Factory

}