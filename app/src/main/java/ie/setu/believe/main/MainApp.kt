package ie.setu.believe.main

import android.app.Application
import ie.setu.believe.models.BelieveMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val believe = BelieveMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Believe started")
    }
}