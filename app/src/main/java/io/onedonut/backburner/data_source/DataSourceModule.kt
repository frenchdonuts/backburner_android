package io.onedonut.backburner.data_source

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import io.onedonut.backburner.Database
import javax.inject.Singleton

@Module
object DataSourceModule {

    @Singleton
    @Provides
    @JvmStatic
    fun db(context: Context) = Database(
        AndroidSqliteDriver(
            Database.Schema,
            context,
            "backburner.db"
        )
    )

}