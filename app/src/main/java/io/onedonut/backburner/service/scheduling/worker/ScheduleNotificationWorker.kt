package io.onedonut.backburner.service.scheduling.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.onedonut.backburner.service.scheduling.Scheduler
import javax.inject.Inject
import javax.inject.Provider

class ScheduleNotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val scheduler: Scheduler
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val randomNoteText: String = TODO("Not defined")
        scheduler.scheduleRandomNotification(randomNoteText)
    }

    class Factory @Inject constructor(
        private val context: Provider<Context>,
        private val scheduler: Provider<Scheduler>
    ) : IWorkerFactory<ScheduleNotificationWorker> {
        override fun create(params: WorkerParameters): ScheduleNotificationWorker {
            return ScheduleNotificationWorker(context.get(), params, scheduler.get())
        }
    }

}