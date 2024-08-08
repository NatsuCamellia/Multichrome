package net.natsucamellia.multichrome

import android.app.Application
import android.util.Log
import net.natsucamellia.multichrome.data.AppContainer
import net.natsucamellia.multichrome.data.DefaultAppContainer

class MultichromeApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        Log.i("Application", "onCreate")
        super.onCreate()
        container = DefaultAppContainer()
    }
}