package io.onedonut.backburner

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.onedonut.backburner.dal.DataAccessLayerModule
import io.onedonut.backburner.data_source.DataSourceModule
import io.onedonut.backburner.service.scheduling.WorkerModule
import io.onedonut.backburner.view_notes.ViewNotesComponent
import io.onedonut.backburner.write_note.WriteNoteComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppSubcomponents::class,
        DataAccessLayerModule::class,
        DataSourceModule::class,
        WorkerModule::class
    ]
)
interface AppComponent: AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun viewNotesComponent(): ViewNotesComponent.Factory
    fun writeNoteComponent(): WriteNoteComponent.Factory

}