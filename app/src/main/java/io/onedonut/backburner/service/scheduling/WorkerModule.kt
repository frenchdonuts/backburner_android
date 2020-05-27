package io.onedonut.backburner.service.scheduling

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.onedonut.backburner.service.scheduling.worker.ShowNotificationWorker
import io.onedonut.backburner.service.scheduling.worker.IWorkerFactory
import io.onedonut.backburner.service.scheduling.worker.ScheduleNotificationWorker

@Module(includes = [WorkerModule.WorkerManagerModule::class])
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(ShowNotificationWorker::class)
    fun bindShowNotificationWorker(factory: ShowNotificationWorker.Factory): IWorkerFactory<out ListenableWorker>

    @Binds
    @IntoMap
    @WorkerKey(ScheduleNotificationWorker::class)
    fun bindScheduleNotificationWorker(factory: ScheduleNotificationWorker.Factory): IWorkerFactory<out ListenableWorker>

    @Module
    object WorkerManagerModule {
        @Provides @JvmStatic fun workManager(context: Context) = WorkManager.getInstance(context)

    }
}

