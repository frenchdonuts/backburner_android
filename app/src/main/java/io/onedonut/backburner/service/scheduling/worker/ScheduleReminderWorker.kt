package io.onedonut.backburner.service.scheduling.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.onedonut.backburner.service.scheduling.Scheduler
import javax.inject.Inject
import javax.inject.Provider

class ScheduleReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val scheduler: Scheduler
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        return try {
            scheduler.scheduleReminder().get()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    class Factory @Inject constructor(
        private val context: Provider<Context>,
        private val scheduler: Provider<Scheduler>
    ) : IWorkerFactory<ScheduleReminderWorker> {
        override fun create(params: WorkerParameters): ScheduleReminderWorker {
            return ScheduleReminderWorker(context.get(), params, scheduler.get())
        }
    }

}