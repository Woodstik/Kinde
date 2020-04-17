package com.wstik.kinde

import android.app.Application
import com.wstik.kinde.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class KindeApp : Application(){

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@KindeApp)
            modules(listOf(appModule, remoteModule, repositoryModule, domainModule, viewModelModule))
        }
    }
}