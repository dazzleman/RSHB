package ic218.ru.rshb

import android.app.Application
import ic218.ru.rshb.di.appModule
import ic218.ru.rshb.di.networkModule
import ic218.ru.rshb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                networkModule,
                viewModelModule
            )
        }
    }
}