package io.onedonut.backburner.service.scheduling.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.onedonut.backburner.service.Notifier
import javax.inject.Inject
import javax.inject.Provider

class RemindUserWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val notifier: Notifier
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        TODO("Only partially implemented")
        // TODO: Query Repo for a random note to show
        val notificationText = inputData.getString(InputData.KEY_NOTIFICATION_TEXT)
        notificationText?.let {
            notifier.notify(it)
        }
    }

    class InputData private constructor() {
        companion object {
            val KEY_NOTIFICATION_TEXT = "notification_text"
        }
    }

    class Factory @Inject constructor(
        private val context: Provider<Context>,
        private val notifier: Provider<Notifier>
    ) : IWorkerFactory<RemindUserWorker> {
        override fun create(params: WorkerParameters): RemindUserWorker {
            return RemindUserWorker(
                context.get(),
                params,
                notifier.get()
            )
        }
    }

}