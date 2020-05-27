package io.onedonut.backburner.service.scheduling

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import io.onedonut.backburner.service.scheduling.worker.ScheduleNotificationWorker
import io.onedonut.backburner.service.scheduling.worker.ShowNotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class Scheduler @Inject constructor(private val workManager: WorkManager) {

    fun scheduleRandomNotification(text: String) {
        scheduleNotification(text, Random.nextLong(7, 15))
    }

    fun scheduleNotification(text: String, daysFromNow: Long) {
        val inputData = workDataOf(
            ShowNotificationWorker.InputData.KEY_NOTIFICATION_TEXT to text
        )
        val showNotificationRequest = OneTimeWorkRequestBuilder<ShowNotificationWorker>()
            .setInitialDelay(daysFromNow, TimeUnit.DAYS)
            .setInputData(inputData)
            .build()
        workManager.enqueue(showNotificationRequest)
    }

    fun makeSureNotificationIsScheduled() {
        val scheduleScheduleNotificationRequest =
            PeriodicWorkRequestBuilder<ScheduleNotificationWorker>(7, TimeUnit.DAYS)
                .build()

        workManager.enqueue(scheduleScheduleNotificationRequest)
    }

}