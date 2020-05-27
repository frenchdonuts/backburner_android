package io.onedonut.backburner.service.scheduling.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.onedonut.backburner.service.Notifier
import javax.inject.Inject
import javax.inject.Provider

class ShowNotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val notifier: Notifier
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val notificationText = inputData.getString(InputData.KEY_NOTIFICATION_TEXT)
        notificationText?.let {
            notifier.notify(it)
        }
        TODO("Return Result")
    }

    class InputData private constructor() {
        companion object {
            val KEY_NOTIFICATION_TEXT = "notification_text"
        }
    }

    class Factory @Inject constructor(
        private val context: Provider<Context>,
        private val notifier: Provider<Notifier>
    ) : IWorkerFactory<ShowNotificationWorker> {
        override fun create(params: WorkerParameters): ShowNotificationWorker {
            return ShowNotificationWorker(
                context.get(),
                params,
                notifier.get()
            )
        }
    }

}