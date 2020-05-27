package io.onedonut.backburner

import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(
            applicationContext
        )
    }
    @Inject lateinit var workerFactory: DaggerAwareWorkerFactory

    override fun onCreate() {
        super.onCreate()
        configureWorkerFactory()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent//DaggerAppComponent.factory().create(applicationContext)
    }

    private fun configureWorkerFactory() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(this, config)
    }
}