package ic218.ru.rshb.manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import ic218.ru.rshb.domain.repositories.TasksRepository
import org.koin.java.KoinJavaComponent.getKoin

class SyncWorkDbRemote(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val tasksRepository: TasksRepository = getKoin().get()


    override suspend fun doWork(): Result {


        return Result.success()
    }
}

val syncWorkOneRequest =
    OneTimeWorkRequestBuilder<SyncWorkDbRemote>()
        .build()

const val SYNC_DB_WORK = "102"
