package io.onedonut.backburner.dal

import dagger.Module
import dagger.Provides
import io.onedonut.backburner.Database
import javax.inject.Singleton

@Module
class DataAccessLayerModule {

    @Singleton
    @Provides
    fun noteRepository(db: Database): NoteRepository {
        return NoteRepositoryImpl(db)
    }

}