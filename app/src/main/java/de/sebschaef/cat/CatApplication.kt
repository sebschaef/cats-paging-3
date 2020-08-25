package de.sebschaef.cat

import android.app.Application
import android.content.Context

class CatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
    }

}