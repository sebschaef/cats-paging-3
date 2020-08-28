package de.sebschaef.cat

import android.app.Application
import de.sebschaef.cat.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CatApplication)
            modules(appModule)
        }
    }

}