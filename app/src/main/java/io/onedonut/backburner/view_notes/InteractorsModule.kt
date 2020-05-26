package io.onedonut.backburner.view_notes

import dagger.Module
import dagger.Provides
import io.onedonut.backburner.dal.NoteRepository
import io.onedonut.backburner.view_notes.interactors.Interactors
import io.onedonut.backburner.view_notes.interactors.InteractorsImpl

@Module
class InteractorsModule {

    @Provides
    fun interactors(noteRepo: NoteRepository): Interactors {
        return InteractorsImpl(noteRepo)
    }

}