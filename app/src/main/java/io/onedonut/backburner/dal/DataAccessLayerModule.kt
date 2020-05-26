package io.onedonut.backburner.dal

import dagger.Module
import dagger.Provides
import io.onedonut.backburner.Database

@Module
class DataAccessLayerModule {

    @Provides
    fun noteRepository(db: Database): NoteRepository {
        return NoteRepositoryImpl(db)
    }

}