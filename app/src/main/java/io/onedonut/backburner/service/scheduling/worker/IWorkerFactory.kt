package io.onedonut.backburner.service.scheduling.worker

import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface IWorkerFactory<T : ListenableWorker> {

    fun create(params: WorkerParameters): T

}