package io.onedonut.backburner

import dagger.Module
import io.onedonut.backburner.view_notes.ViewNotesComponent
import io.onedonut.backburner.write_note.WriteNoteComponent

@Module(subcomponents = [ViewNotesComponent::class, WriteNoteComponent::class])
class AppSubcomponents