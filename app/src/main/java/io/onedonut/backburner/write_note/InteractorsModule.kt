package io.onedonut.backburner.write_note

import dagger.Module
import dagger.Provides
import io.onedonut.backburner.dal.NoteRepository
import io.onedonut.backburner.write_note.interactors.Interactors
import io.onedonut.backburner.write_note.interactors.InteractorsImpl

@Module
class InteractorsModule {

    @Provides
    fun interactors(noteRepo: NoteRepository): Interactors {
        return InteractorsImpl(noteRepo)
    }

}