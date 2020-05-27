package io.onedonut.backburner.service.scheduling

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.onedonut.backburner.service.scheduling.worker.RemindUserWorker
import io.onedonut.backburner.service.scheduling.worker.IWorkerFactory
import io.onedonut.backburner.service.scheduling.worker.ScheduleReminderWorker

@Module(includes = [WorkerModule.WorkerManagerModule::class])
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RemindUserWorker::class)
    fun bindShowNotificationWorker(factory: RemindUserWorker.Factory): IWorkerFactory<out ListenableWorker>

    @Binds
    @IntoMap
    @WorkerKey(ScheduleReminderWorker::class)
    fun bindScheduleNotificationWorker(factory: ScheduleReminderWorker.Factory): IWorkerFactory<out ListenableWorker>

    @Module
    object WorkerManagerModule {

        @Provides @JvmStatic fun workManager(context: Context) = WorkManager.getInstance(context)

    }

}

