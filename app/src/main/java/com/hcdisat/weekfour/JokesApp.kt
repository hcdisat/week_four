package com.hcdisat.weekfour

import android.app.Application
import com.hcdisat.weekfour.di.databaseModule
import com.hcdisat.weekfour.di.restApiModule
import com.hcdisat.weekfour.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JokesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@JokesApp)
            modules(
                listOf(
                    restApiModule,
                    viewModelsModule,
                    databaseModule
                )
            )
        }
    }
}