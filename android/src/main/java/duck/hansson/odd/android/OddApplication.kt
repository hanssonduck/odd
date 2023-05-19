package duck.hansson.odd.android

import android.app.Application
import duck.hansson.odd.shared.module.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class OddApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@OddApplication)
        }
    }
}
