package io.onedonut.backburner.service.scheduling

import androidx.work.*
import com.google.common.util.concurrent.ListenableFuture
import io.onedonut.backburner.service.scheduling.worker.ScheduleReminderWorker
import io.onedonut.backburner.service.scheduling.worker.RemindUserWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class Scheduler @Inject constructor(private val workManager: WorkManager) {

    fun scheduleReminder(): ListenableFuture<Operation.State.SUCCESS> {
        TODO("Logic only partially implemented")
        // TODO: Check if RemindUserWorker is already scheduled. If not, schedule it.
        val daysFromNow = Random.nextLong(7, 15)
        val remindUserRequest = OneTimeWorkRequestBuilder<RemindUserWorker>()
            .setInitialDelay(daysFromNow, TimeUnit.DAYS)
            .build()
        return workManager.enqueue(remindUserRequest).result
    }

    fun makeSureReminderIsScheduled() {
        TODO("Logic only partially implemented")
        // TODO: Check if SchedulerReminderWorker is already scheduled. If not, schedule it.
        workManager.enqueue(
            PeriodicWorkRequestBuilder<ScheduleReminderWorker>(7, TimeUnit.DAYS).build()
        )
    }

}