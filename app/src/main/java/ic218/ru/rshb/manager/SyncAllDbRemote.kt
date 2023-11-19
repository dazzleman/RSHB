package ic218.ru.rshb.manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import ic218.ru.rshb.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.first
import org.koin.java.KoinJavaComponent.getKoin
import java.util.concurrent.TimeUnit

class SyncAllDbRemote(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    private val profileRepository: ProfileRepository = getKoin().get()


    override suspend fun doWork(): Result {
        profileRepository.syncUsers().first()


        return Result.success()
    }
}

val syncDataConstraints = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .build()

val syncPeriodicOneRequest =
    PeriodicWorkRequestBuilder<SyncAllDbRemote>(1, TimeUnit.HOURS)
        .setConstraints(syncDataConstraints)
        .build()

const val SYNC_DB_PERIODIC = "101"
